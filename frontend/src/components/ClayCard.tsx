import React from 'react';
import { View, StyleSheet, ViewStyle } from 'react-native';
import { Colors, ClayStyles, normalize } from '../constants/Theme';

interface ClayCardProps {
    children: React.ReactNode;
    style?: ViewStyle;
}

export const ClayCard = ({ children, style }: ClayCardProps) => {
    return (
        <View style={[ClayStyles.card, style]}>
            {/* Simulo l'effetto luce interna con un bordo superiore sottile */}
            <View style={[styles.innerLight, ClayStyles.innerEffectTop]}>
                {children}
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    innerLight: {
        borderRadius: normalize(28),
        overflow: 'hidden',
    }
});