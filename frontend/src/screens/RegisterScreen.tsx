import React, {useState} from 'react';
import {Alert, StyleSheet, Text, TextInput, TouchableOpacity, View} from 'react-native';
import {BlurView} from 'expo-blur';
import {normalize} from '../constants/Theme';
import {useUser} from '../context/UserContext';
import {saveAuthData} from '../utils/authStorage';
import {authService} from '../api/authService'; // <-- Cambiato import

export const RegisterScreen = ({navigation}: any) => {
    const {setUser} = useUser();
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleRegister = async () => {
        if (!nome || !email || !password) {
            Alert.alert("Errore", "Tutti i campi sono obbligatori.");
            return;
        }

        try {
            const data = await authService.register(nome, email, password);

            if (data.token && data.user) {
                await saveAuthData(data.token, data.user);
                setUser(data.user);
            }
        } catch (error: any) {
            Alert.alert("Errore", error.message);
        }
    };

    return (
        <View style={styles.mainContainer}>
            <View style={[styles.bgBlob, styles.blob1]}/>
            <View style={[styles.bgBlob, styles.blob2]}/>

            <View style={styles.content}>
                <BlurView intensity={40} tint="light" style={styles.glassCard}>
                    <Text style={styles.title}>Nuovo Account</Text>
                    <Text style={styles.subtitle}>Unisciti per iniziare a condividere.</Text>

                    <TextInput
                        style={styles.input}
                        placeholder="Il tuo Nome"
                        placeholderTextColor="#999"
                        value={nome}
                        onChangeText={setNome}
                    />

                    <TextInput
                        style={styles.input}
                        placeholder="Email"
                        placeholderTextColor="#999"
                        keyboardType="email-address"
                        autoCapitalize="none"
                        value={email}
                        onChangeText={setEmail}
                    />

                    <TextInput
                        style={styles.input}
                        placeholder="Password (min 6 caratteri)"
                        placeholderTextColor="#999"
                        secureTextEntry
                        value={password}
                        onChangeText={setPassword}
                    />

                    <TouchableOpacity style={styles.primaryButton} onPress={handleRegister}>
                        <Text style={styles.buttonText}>Registrati</Text>
                    </TouchableOpacity>

                    <TouchableOpacity onPress={() => navigation.navigate('Login')}>
                        <Text style={styles.linkText}>Hai già un account? Accedi</Text>
                    </TouchableOpacity>
                </BlurView>
            </View>
        </View>
    );
};

// GLI STILI RIMANGONO INVARIATI COME RICHIESTO
const styles = StyleSheet.create({
    mainContainer: {flex: 1, backgroundColor: '#1E0B2D'},
    bgBlob: {position: 'absolute', borderRadius: 500, opacity: 0.6},
    blob1: {width: 400, height: 400, backgroundColor: '#4B1D52', top: -100, left: -100},
    blob2: {width: 450, height: 450, backgroundColor: '#6A248A', bottom: -50, right: -150},
    content: {flex: 1, justifyContent: 'center', padding: normalize(24)},
    glassCard: {
        padding: normalize(30),
        borderRadius: normalize(24),
        borderWidth: 1,
        borderColor: 'rgba(253, 246, 227, 0.25)',
        alignItems: 'center'
    },
    title: {fontSize: normalize(32), fontWeight: '800', color: '#FDF6E3', marginBottom: normalize(8)},
    subtitle: {
        fontSize: normalize(16),
        color: 'rgba(253, 246, 227, 0.7)',
        marginBottom: normalize(30),
        textAlign: 'center'
    },
    input: {
        width: '100%',
        backgroundColor: 'rgba(255, 255, 255, 0.9)',
        borderRadius: normalize(12),
        padding: normalize(14),
        marginBottom: normalize(16),
        fontSize: normalize(16),
        color: '#333'
    },
    primaryButton: {
        backgroundColor: '#6A248A',
        paddingVertical: normalize(14),
        borderRadius: normalize(12),
        width: '100%',
        alignItems: 'center',
        marginTop: normalize(8)
    },
    buttonText: {color: '#FFFFFF', fontSize: normalize(16), fontWeight: 'bold'},
    linkText: {color: '#FDF6E3', marginTop: normalize(20), fontSize: normalize(14), textDecorationLine: 'underline'}
});