import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, FlatList, ActivityIndicator, SafeAreaView, Dimensions } from 'react-native';
import { BlurView } from 'expo-blur';
import { Colors, normalize } from '../constants/Theme';
import { Texts } from '../constants/Texts';
import { clearAuthData } from '../utils/authStorage';
import { useUser } from "../context/UserContext";
import { calendarService } from '../api/calendarService';
import { CalendarEvent } from '../types/Event';

const { width, height } = Dimensions.get('window');

export const HomeScreen = () => {
    const { user, setUser } = useUser();
    const [events, setEvents] = useState<CalendarEvent[]>([]);
    const [loading, setLoading] = useState(true);

    const fetchEvents = async () => {
        try {
            setLoading(true);
            const data = await calendarService.getEvents();
            const sortedData = data.sort((a, b) => new Date(a.dataInizio).getTime() - new Date(b.dataInizio).getTime());
            setEvents(sortedData);
        } catch (error) {
            console.log("Errore recupero eventi:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchEvents();
    }, []);

    const handleLogout = async () => {
        await clearAuthData();
        setUser(null);
    };

    const renderEvent = ({ item }: { item: CalendarEvent }) => {
        const startTime = new Date(item.dataInizio).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        const endTime = new Date(item.dataFine).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

        return (
            <View style={styles.cardContainer}>
                <BlurView intensity={40} tint="light" style={styles.glassCard}>
                    <View style={styles.cardHeader}>
                        <Text style={styles.title}>{item.titolo}</Text>
                        <View style={styles.timeBadge}>
                            <Text style={styles.timeText}>{startTime} - {endTime}</Text>
                        </View>
                    </View>
                    {item.descrizione ? (
                        <Text style={styles.description} numberOfLines={2}>
                            {item.descrizione}
                        </Text>
                    ) : null}
                </BlurView>
            </View>
        );
    };

    const today = new Date().toLocaleDateString('it-IT', { weekday: 'long', day: 'numeric', month: 'long' });
    const formattedToday = today.charAt(0).toUpperCase() + today.slice(1);

    return (
        <View style={styles.mainContainer}>
            {/* Sfondo Astratto Fluido */}
            <View style={[styles.bgBlob, styles.blob1]} />
            <View style={[styles.bgBlob, styles.blob2]} />
            <View style={[styles.bgBlob, styles.blob3]} />

            <SafeAreaView style={styles.safeArea}>
                <View style={styles.header}>
                    <View>
                        <Text style={styles.dateText}>{formattedToday}</Text>
                        <Text style={styles.welcomeText}>Ciao, {user?.nome || 'Utente'}</Text>
                    </View>
                    <TouchableOpacity onPress={handleLogout} style={styles.logoutButton}>
                        <BlurView intensity={20} tint="light" style={styles.logoutGlass}>
                            <Text style={styles.logoutText}>Esci</Text>
                        </BlurView>
                    </TouchableOpacity>
                </View>

                <View style={styles.content}>
                    {loading ? (
                        <ActivityIndicator size="large" color="#FDF6E3" style={styles.loader} />
                    ) : (
                        <FlatList
                            data={events}
                            keyExtractor={(item) => item.id}
                            renderItem={renderEvent}
                            contentContainerStyle={styles.listContainer}
                            showsVerticalScrollIndicator={false}
                            ListEmptyComponent={
                                <View style={styles.emptyContainer}>
                                    <BlurView intensity={30} tint="light" style={styles.emptyGlass}>
                                        <Text style={styles.emptyTitle}>Nessun impegno</Text>
                                        <Text style={styles.emptySubtitle}>La tua agenda è libera per oggi.</Text>
                                    </BlurView>
                                </View>
                            }
                        />
                    )}
                </View>
            </SafeAreaView>
        </View>
    );
};

const styles = StyleSheet.create({
    mainContainer: {
        flex: 1,
        backgroundColor: '#1E0B2D', // Viola scuro profondo come base
    },
    // Forme astratte per l'effetto Glassmorphism
    bgBlob: {
        position: 'absolute',
        borderRadius: width,
        opacity: 0.6,
    },
    blob1: {
        width: width * 0.8,
        height: width * 0.8,
        backgroundColor: '#6A248A', // Viola intermedio
        top: -width * 0.2,
        left: -width * 0.2,
    },
    blob2: {
        width: width * 0.9,
        height: width * 0.9,
        backgroundColor: '#4B1D52', // Viola prugna
        bottom: height * 0.1,
        right: -width * 0.3,
    },
    blob3: {
        width: width * 0.6,
        height: width * 0.6,
        backgroundColor: '#8C3A8D', // Lilla acceso
        top: height * 0.3,
        left: width * 0.1,
    },
    safeArea: {
        flex: 1,
    },
    header: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'flex-end',
        paddingHorizontal: normalize(24),
        paddingTop: normalize(40),
        paddingBottom: normalize(20),
    },
    dateText: {
        fontSize: normalize(13),
        color: 'rgba(253, 246, 227, 0.7)', // Panna semi-trasparente
        textTransform: 'uppercase',
        fontWeight: '600',
        marginBottom: normalize(4),
        letterSpacing: 1,
    },
    welcomeText: {
        fontSize: normalize(28),
        fontWeight: '800',
        color: '#FDF6E3', // Color Panna
        letterSpacing: -0.5,
    },
    logoutButton: {
        overflow: 'hidden',
        borderRadius: normalize(20),
    },
    logoutGlass: {
        paddingHorizontal: normalize(16),
        paddingVertical: normalize(8),
        backgroundColor: 'rgba(253, 246, 227, 0.1)',
        borderWidth: 1,
        borderColor: 'rgba(253, 246, 227, 0.2)',
    },
    logoutText: {
        color: '#FDF6E3',
        fontWeight: '600',
        fontSize: normalize(14),
    },
    content: {
        flex: 1,
    },
    loader: {
        marginTop: normalize(40),
    },
    listContainer: {
        paddingTop: normalize(20),
        paddingBottom: normalize(40),
        paddingHorizontal: normalize(24),
    },
    cardContainer: {
        marginBottom: normalize(20),
        borderRadius: normalize(24),
        overflow: 'hidden', // Necessario per contenere il BlurView
    },
    glassCard: {
        padding: normalize(20),
        backgroundColor: 'rgba(253, 246, 227, 0.08)', // Panna molto trasparente
        borderWidth: 1,
        borderColor: 'rgba(253, 246, 227, 0.25)', // Bordo panna semi-trasparente
        borderRadius: normalize(24),
    },
    cardHeader: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'flex-start',
        marginBottom: normalize(12),
    },
    title: {
        flex: 1,
        fontSize: normalize(18),
        fontWeight: '700',
        color: '#FDF6E3',
        marginRight: normalize(10),
        letterSpacing: 0.3,
    },
    timeBadge: {
        backgroundColor: 'rgba(253, 246, 227, 0.15)',
        paddingHorizontal: normalize(10),
        paddingVertical: normalize(4),
        borderRadius: normalize(12),
        borderWidth: 1,
        borderColor: 'rgba(253, 246, 227, 0.3)',
    },
    timeText: {
        fontSize: normalize(12),
        fontWeight: '700',
        color: '#FDF6E3',
    },
    description: {
        fontSize: normalize(14),
        color: 'rgba(253, 246, 227, 0.7)',
        lineHeight: normalize(20),
    },
    emptyContainer: {
        alignItems: 'center',
        justifyContent: 'center',
        marginTop: normalize(60),
        borderRadius: normalize(24),
        overflow: 'hidden',
    },
    emptyGlass: {
        padding: normalize(30),
        alignItems: 'center',
        backgroundColor: 'rgba(253, 246, 227, 0.05)',
        borderWidth: 1,
        borderColor: 'rgba(253, 246, 227, 0.15)',
        borderRadius: normalize(24),
        width: '100%',
    },
    emptyTitle: {
        fontSize: normalize(20),
        fontWeight: '700',
        color: '#FDF6E3',
        marginBottom: normalize(8),
    },
    emptySubtitle: {
        fontSize: normalize(15),
        color: 'rgba(253, 246, 227, 0.6)',
        fontWeight: '500',
        textAlign: 'center',
    },
});