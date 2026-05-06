import { Dimensions, PixelRatio } from 'react-native';

const { width, height } = Dimensions.get('window');
const scale = width / 375;

// Funzione per scalare dimensioni in base allo schermo
export const normalize = (size: number) => {
    const newSize = size * scale;
    return Math.round(PixelRatio.roundToNearestPixel(newSize));
};

export const Colors = {
    primary: '#8294FF',
    background: '#E0E7FF', // Colore pastello tipico clay
    white: '#FFFFFF',
    text: '#2D3436',
    shadowLight: '#FFFFFF',
    shadowDark: '#B2B9D1',
};

export const ClayStyles = {
    card: {
        backgroundColor: Colors.background,
        borderRadius: normalize(30),
        padding: normalize(20),
        // Ombra esterna (Claymorphism)
        shadowColor: Colors.shadowDark,
        shadowOffset: { width: 8, height: 8 },
        shadowOpacity: 0.5,
        shadowRadius: 10,
        elevation: 10, // Per Android
    },
    // In React Native le ombre interne si simulano con bordi chiari/scuri
    innerEffectTop: {
        borderTopWidth: 2,
        borderLeftWidth: 2,
        borderColor: Colors.shadowLight,
    }
};