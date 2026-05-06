import React, {useCallback, useEffect, useState} from 'react';
import {
    ActivityIndicator,
    Alert,
    Dimensions,
    FlatList,
    Modal,
    StyleSheet,
    Switch,
    Text,
    TextInput,
    TouchableOpacity,
    View
} from 'react-native';
import {SafeAreaView} from 'react-native-safe-area-context';
import {BlurView} from 'expo-blur';
import {Calendar as CalendarIcon, Plus} from 'lucide-react-native';
import {normalize} from '../constants/Theme';
import {clearAuthData} from '../utils/authStorage';
import {useUser} from "../context/UserContext";
import {calendarService} from '../api/calendarService';
import {CalendarEvent} from '../types/Event';

const {width, height} = Dimensions.get('window');

type ViewMode = 'me' | 'shared';

export const HomeScreen = () => {
    const {user, setUser} = useUser();
    const [events, setEvents] = useState<CalendarEvent[]>([]);
    const [loading, setLoading] = useState(true);
    const [viewMode, setViewMode] = useState<ViewMode>('me');

    // Stati Gruppi
    const [userGroupId, setUserGroupId] = useState<string | null>(null);
    const [showGroupModal, setShowGroupModal] = useState(false);
    const [inviteCode, setInviteCode] = useState('');
    const [newGroupName, setNewGroupName] = useState('');

    // Stati Nuovo Evento
    const [showEventModal, setShowEventModal] = useState(false);
    const [eventTitle, setEventTitle] = useState('');
    const [eventDesc, setEventDesc] = useState('');
    const [isAllDay, setIsAllDay] = useState(false);
    const [selectedDate, setSelectedDate] = useState(new Date());

    const checkGroupAndFetch = useCallback(async () => {
        try {
            setLoading(true);
            if (viewMode === 'me') {
                const data = await calendarService.getPersonalEvents();
                setEvents(data.toSorted((a, b) => new Date(a.dataInizio).getTime() - new Date(b.dataInizio).getTime()));
            } else {
                const groups = await calendarService.getUserGroups(user?.id || '');
                if (groups && groups.length > 0) {
                    const gId = groups[0].group.id;
                    setUserGroupId(gId);
                    const data = await calendarService.getGroupEvents(gId);
                    setEvents(data.toSorted((a, b) => new Date(a.dataInizio).getTime() - new Date(b.dataInizio).getTime()));
                } else {
                    setUserGroupId(null);
                    setEvents([]);
                }
            }
        } catch (error) {
            console.error("Fetch Error:", error);
            Alert.alert("Errore di Caricamento", "Non è stato possibile recuperare gli impegni.");
        } finally {
            setLoading(false);
        }
    }, [viewMode, user?.id]);

    useEffect(() => {
        checkGroupAndFetch();
    }, [checkGroupAndFetch]);

    const handleCreateEvent = async () => {
        if (!eventTitle) {
            Alert.alert("Dato mancante", "Inserisci almeno un titolo per l'impegno.");
            return;
        }

        try {
            const newEvent: Partial<CalendarEvent> = {
                titolo: eventTitle,
                descrizione: eventDesc,
                dataInizio: selectedDate.toISOString(),
                tuttoGiorno: isAllDay,
                gruppoId: viewMode === 'shared' ? userGroupId! : undefined
            };

            await calendarService.createEvent(newEvent);
            setShowEventModal(false);
            setEventTitle('');
            setEventDesc('');
            setIsAllDay(false);
            setSelectedDate(new Date());
            await checkGroupAndFetch();
        } catch (error) {
            console.error("Create Event Error:", error);
            Alert.alert("Errore di Salvataggio", "Impossibile salvare l'impegno.");
        }
    };

    const handleCreateGroup = async () => {
        if (!newGroupName.trim()) {
            Alert.alert("Attenzione", "Inserisci un nome valido per il gruppo.");
            return;
        }
        try {
            const group = await calendarService.createGroup(newGroupName);
            Alert.alert("Gruppo Creato", `Invia questo codice: ${group.codiceInvito}`);
            setShowGroupModal(false);
            setNewGroupName('');
            await checkGroupAndFetch();
        } catch (error) {
            console.error("Create Group Error:", error);
            Alert.alert("Errore", "Impossibile creare il gruppo condiviso.");
        }
    };

    const handleJoinGroup = async () => {
        if (!inviteCode.trim()) {
            Alert.alert("Attenzione", "Inserisci il codice ricevuto dal tuo partner.");
            return;
        }
        try {
            await calendarService.joinGroup(inviteCode);
            setShowGroupModal(false);
            setInviteCode('');
            await checkGroupAndFetch();
        } catch (error) {
            console.error("Join Group Error:", error);
            Alert.alert("Accesso Negato", "Il codice inserito non è valido.");
        }
    };

    const handleLogout = async () => {
        try {
            await clearAuthData();
            setUser(null);
        } catch (error) {
            console.error("Logout Error:", error);
        }
    };

    const renderEvent = ({item}: { item: CalendarEvent }) => {
        const startTime = new Date(item.dataInizio).toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});
        return (
            <View style={styles.cardContainer}>
                <BlurView intensity={40} tint="light" style={styles.glassCard}>
                    <View style={styles.cardHeader}>
                        <Text style={styles.title}>{item.titolo}</Text>
                        <View style={styles.timeBadge}>
                            <Text style={styles.timeText}>
                                {item.tuttoGiorno ? "TUTTO IL GIORNO" : startTime}
                            </Text>
                        </View>
                    </View>
                    {item.descrizione && <Text style={styles.description} numberOfLines={2}>{item.descrizione}</Text>}
                </BlurView>
            </View>
        );
    };

    // RISOLUZIONE SONAR: Estrazione della logica di rendering indipendente
    const renderContent = () => {
        if (loading) {
            return <ActivityIndicator size="large" color="#FDF6E3" style={styles.loader}/>;
        }

        if (viewMode === 'shared' && !userGroupId) {
            return (
                <View style={styles.setupContainer}>
                    <BlurView intensity={40} tint="light" style={styles.setupCard}>
                        <Text style={styles.setupTitle}>Calendario Condiviso</Text>
                        <Text style={styles.setupSubtitle}>Crea un gruppo o inserisci un codice per collaborare.</Text>
                        <TouchableOpacity style={styles.setupButton} onPress={() => setShowGroupModal(true)}>
                            <Text style={styles.setupButtonText}>Configura Ora</Text>
                        </TouchableOpacity>
                    </BlurView>
                </View>
            );
        }

        return (
            <FlatList
                data={events}
                keyExtractor={(item) => item.id}
                renderItem={renderEvent}
                contentContainerStyle={styles.listContainer}
                ListEmptyComponent={<View style={styles.emptyContainer}><Text style={styles.emptyTitle}>Agenda
                    libera</Text></View>}
            />
        );
    };

    return (
        <View style={styles.mainContainer}>
            <View style={[styles.bgBlob, styles.blob1]}/>
            <View style={[styles.bgBlob, styles.blob2]}/>
            <View style={[styles.bgBlob, styles.blob3]}/>

            <SafeAreaView style={styles.safeArea}>
                <View style={styles.header}>
                    <View>
                        <Text style={styles.dateText}>IL TUO PROGRAMMA</Text>
                        <Text style={styles.welcomeText}>Ciao, {user?.nome}</Text>
                    </View>
                    <TouchableOpacity onPress={handleLogout} style={styles.logoutButton}>
                        <BlurView intensity={20} tint="light" style={styles.logoutGlass}>
                            <Text style={styles.logoutText}>Esci</Text>
                        </BlurView>
                    </TouchableOpacity>
                </View>

                <View style={styles.swapContainer}>
                    <TouchableOpacity onPress={() => setViewMode('me')}
                                      style={[styles.swapButton, viewMode === 'me' && styles.swapActive]}>
                        <Text style={[styles.swapText, viewMode === 'me' && styles.swapTextActive]}>Privata</Text>
                    </TouchableOpacity>
                    <TouchableOpacity onPress={() => setViewMode('shared')}
                                      style={[styles.swapButton, viewMode === 'shared' && styles.swapActive]}>
                        <Text style={[styles.swapText, viewMode === 'shared' && styles.swapTextActive]}>Condivisa</Text>
                    </TouchableOpacity>
                </View>

                <View style={styles.content}>
                    {renderContent()}
                </View>

                {(viewMode === 'me' || (viewMode === 'shared' && userGroupId)) && (
                    <TouchableOpacity style={styles.fab} onPress={() => setShowEventModal(true)}>
                        <Plus color="#FFF" size={32}/>
                    </TouchableOpacity>
                )}

                {/* MODALE CREAZIONE EVENTO */}
                <Modal visible={showEventModal} transparent animationType="slide">
                    <View style={styles.modalOverlay}>
                        <BlurView intensity={90} tint="dark" style={styles.modalContent}>
                            <Text style={styles.modalTitle}>Nuovo Impegno</Text>
                            <TextInput
                                style={styles.modalInput}
                                placeholder="Cosa devi fare?"
                                placeholderTextColor="#999"
                                value={eventTitle}
                                onChangeText={setEventTitle}
                            />

                            <TouchableOpacity style={styles.datePickerToggle}>
                                <CalendarIcon color="#FDF6E3" size={20}/>
                                <Text style={styles.datePickerText}>
                                    {selectedDate.toLocaleDateString('it-IT')}
                                </Text>
                            </TouchableOpacity>

                            <TextInput
                                style={[styles.modalInput, {height: 80}]}
                                placeholder="Descrizione (opzionale)"
                                placeholderTextColor="#999"
                                multiline
                                value={eventDesc}
                                onChangeText={setEventDesc}
                            />

                            <View style={styles.switchRow}>
                                <Text style={styles.switchLabel}>Tutto il giorno</Text>
                                <Switch
                                    value={isAllDay}
                                    onValueChange={setIsAllDay}
                                    trackColor={{false: "#767577", true: "#8C3A8D"}}
                                />
                            </View>

                            <TouchableOpacity style={styles.modalButton} onPress={handleCreateEvent}>
                                <Text style={styles.modalButtonText}>Salva Impegno</Text>
                            </TouchableOpacity>
                            <TouchableOpacity onPress={() => setShowEventModal(false)}>
                                <Text style={styles.closeButtonText}>Annulla</Text>
                            </TouchableOpacity>
                        </BlurView>
                    </View>
                </Modal>

                {/* MODALE GRUPPO */}
                <Modal visible={showGroupModal} transparent animationType="fade">
                    <View style={styles.modalOverlay}>
                        <BlurView intensity={90} tint="dark" style={styles.modalContent}>
                            <Text style={styles.modalTitle}>Configura Condivisione</Text>
                            <TextInput style={styles.modalInput} placeholder="Nome gruppo" placeholderTextColor="#999"
                                       value={newGroupName} onChangeText={setNewGroupName}/>
                            <TouchableOpacity style={styles.modalButton} onPress={handleCreateGroup}>
                                <Text style={styles.modalButtonText}>Crea Nuovo</Text>
                            </TouchableOpacity>
                            <div style={styles.separator}/>
                            <TextInput style={styles.modalInput} placeholder="Codice Invito" placeholderTextColor="#999"
                                       value={inviteCode} onChangeText={setInviteCode} autoCapitalize="characters"/>
                            <TouchableOpacity style={[styles.modalButton, {backgroundColor: '#8C3A8D'}]}
                                              onPress={handleJoinGroup}>
                                <Text style={styles.modalButtonText}>Unisciti</Text>
                            </TouchableOpacity>
                            <TouchableOpacity onPress={() => setShowGroupModal(false)}>
                                <Text style={styles.closeButtonText}>Chiudi</Text>
                            </TouchableOpacity>
                        </BlurView>
                    </View>
                </Modal>
            </SafeAreaView>
        </View>
    );
};

// ... gli styles rimangono gli stessi ...
const styles = StyleSheet.create({
    mainContainer: {flex: 1, backgroundColor: '#1E0B2D'},
    bgBlob: {position: 'absolute', borderRadius: width, opacity: 0.6},
    blob1: {width: width * 0.8, height: width * 0.8, backgroundColor: '#6A248A', top: -width * 0.2, left: -width * 0.2},
    blob2: {
        width: width * 0.9,
        height: width * 0.9,
        backgroundColor: '#4B1D52',
        bottom: height * 0.1,
        right: -width * 0.3
    },
    blob3: {width: width * 0.6, height: width * 0.6, backgroundColor: '#8C3A8D', top: height * 0.3, left: width * 0.1},
    safeArea: {flex: 1},
    header: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'flex-end',
        paddingHorizontal: normalize(24),
        paddingTop: normalize(20),
        paddingBottom: normalize(10)
    },
    dateText: {fontSize: normalize(12), color: 'rgba(253, 246, 227, 0.6)', fontWeight: '700', letterSpacing: 1.5},
    welcomeText: {fontSize: normalize(26), fontWeight: '800', color: '#FDF6E3'},
    logoutButton: {overflow: 'hidden', borderRadius: normalize(20)},
    logoutGlass: {
        paddingHorizontal: normalize(14),
        paddingVertical: normalize(6),
        backgroundColor: 'rgba(253, 246, 227, 0.1)',
        borderWidth: 1,
        borderColor: 'rgba(253, 246, 227, 0.2)'
    },
    logoutText: {color: '#FDF6E3', fontWeight: '600', fontSize: normalize(12)},
    swapContainer: {flexDirection: 'row', paddingHorizontal: normalize(24), marginVertical: normalize(15)},
    swapButton: {
        paddingVertical: normalize(8),
        paddingHorizontal: normalize(22),
        borderRadius: normalize(20),
        marginRight: normalize(12),
        backgroundColor: 'rgba(253, 246, 227, 0.05)'
    },
    swapActive: {backgroundColor: 'rgba(253, 246, 227, 0.25)'},
    swapText: {color: 'rgba(253, 246, 227, 0.4)', fontWeight: '700', fontSize: normalize(14)},
    swapTextActive: {color: '#FDF6E3'},
    content: {flex: 1},
    loader: {marginTop: normalize(50)},
    listContainer: {paddingHorizontal: normalize(24), paddingBottom: normalize(100)},
    cardContainer: {marginBottom: normalize(16), borderRadius: normalize(24), overflow: 'hidden'},
    glassCard: {
        padding: normalize(18),
        backgroundColor: 'rgba(253, 246, 227, 0.07)',
        borderWidth: 1,
        borderColor: 'rgba(253, 246, 227, 0.2)'
    },
    cardHeader: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: normalize(8)
    },
    title: {flex: 1, fontSize: normalize(17), fontWeight: '700', color: '#FDF6E3'},
    timeBadge: {
        backgroundColor: 'rgba(140, 58, 141, 0.3)',
        paddingHorizontal: normalize(8),
        paddingVertical: normalize(4),
        borderRadius: normalize(8)
    },
    timeText: {fontSize: normalize(10), fontWeight: '800', color: '#FDF6E3'},
    description: {fontSize: normalize(13), color: 'rgba(253, 246, 227, 0.6)', lineHeight: normalize(18)},
    emptyContainer: {alignItems: 'center', marginTop: normalize(100)},
    emptyTitle: {fontSize: normalize(16), color: 'rgba(253, 246, 227, 0.4)', fontWeight: '600'},
    fab: {
        position: 'absolute',
        bottom: normalize(30),
        right: normalize(24),
        width: normalize(60),
        height: normalize(60),
        borderRadius: normalize(30),
        backgroundColor: '#6A248A',
        justifyContent: 'center',
        alignItems: 'center',
        elevation: 8,
        shadowColor: '#000',
        shadowOffset: {width: 0, height: 4},
        shadowOpacity: 0.3,
        shadowRadius: 4
    },
    setupContainer: {flex: 1, justifyContent: 'center', alignItems: 'center', padding: normalize(24)},
    setupCard: {
        padding: normalize(30),
        borderRadius: normalize(24),
        alignItems: 'center',
        width: '100%',
        backgroundColor: 'rgba(253, 246, 227, 0.05)'
    },
    setupTitle: {fontSize: normalize(20), fontWeight: '800', color: '#FDF6E3', marginBottom: normalize(10)},
    setupSubtitle: {
        fontSize: normalize(14),
        color: 'rgba(253, 246, 227, 0.6)',
        textAlign: 'center',
        marginBottom: normalize(24)
    },
    setupButton: {
        backgroundColor: '#6A248A',
        paddingVertical: normalize(12),
        paddingHorizontal: normalize(32),
        borderRadius: normalize(12)
    },
    setupButtonText: {color: '#FFF', fontWeight: 'bold'},
    modalOverlay: {flex: 1, backgroundColor: 'rgba(0,0,0,0.7)', justifyContent: 'center', padding: normalize(20)},
    modalContent: {
        padding: normalize(24),
        borderRadius: normalize(28),
        alignItems: 'center',
        borderWidth: 1,
        borderColor: 'rgba(255,255,255,0.1)'
    },
    modalTitle: {fontSize: normalize(22), fontWeight: 'bold', color: '#FFF', marginBottom: normalize(24)},
    modalInput: {
        width: '100%',
        backgroundColor: 'rgba(255,255,255,0.1)',
        borderRadius: normalize(12),
        padding: normalize(15),
        marginBottom: normalize(15),
        color: '#FFF'
    },
    datePickerToggle: {
        flexDirection: 'row',
        alignItems: 'center',
        width: '100%',
        backgroundColor: 'rgba(255,255,255,0.05)',
        borderRadius: normalize(12),
        padding: normalize(15),
        marginBottom: normalize(15)
    },
    datePickerText: {color: '#FDF6E3', marginLeft: normalize(10), fontSize: normalize(16)},
    switchRow: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        width: '100%',
        marginBottom: normalize(24),
        paddingHorizontal: normalize(5)
    },
    switchLabel: {color: '#FDF6E3', fontSize: normalize(16), fontWeight: '600'},
    modalButton: {
        width: '100%',
        backgroundColor: '#6A248A',
        padding: normalize(15),
        borderRadius: normalize(12),
        alignItems: 'center',
        marginBottom: normalize(15)
    },
    modalButtonText: {color: '#FFF', fontWeight: 'bold', fontSize: normalize(16)},
    separator: {height: 1, backgroundColor: 'rgba(255,255,255,0.1)', width: '100%', marginVertical: normalize(20)},
    closeButtonText: {color: 'rgba(255,255,255,0.4)', textDecorationLine: 'underline', marginTop: 10}
});