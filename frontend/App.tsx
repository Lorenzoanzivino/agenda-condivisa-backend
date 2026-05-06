// App.tsx
import React, { useEffect, useState } from 'react';
import { View, ActivityIndicator } from 'react-native'; // <-- Fix import
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { SafeAreaProvider } from 'react-native-safe-area-context';

import { UserProvider, useUser } from './src/context/UserContext';
import { LoginScreen } from './src/screens/LoginScreen';
import { RegisterScreen } from './src/screens/RegisterScreen';
import { HomeScreen } from './src/screens/HomeScreen';
import { getAuthData } from './src/utils/authStorage';
import { Colors } from './src/constants/Theme';

const Stack = createStackNavigator();

const RootNavigator = () => {
  const { user, setUser } = useUser();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const checkAuth = async () => {
      const { token, user: savedUser } = await getAuthData();
      if (token && savedUser) {
        setUser(savedUser);
      }
      setLoading(false);
    };
    checkAuth();
  }, [setUser]);

  if (loading) {
    return (
        <View style={{ flex: 1, justifyContent: 'center', backgroundColor: Colors.background }}>
          <ActivityIndicator size="large" color={Colors.primary} />
        </View>
    );
  }

  return (
      <Stack.Navigator screenOptions={{ headerShown: false }}>
        {user ? (
            <Stack.Screen name="Home" component={HomeScreen} />
        ) : (
            <>
              <Stack.Screen name="Login" component={LoginScreen} />
              <Stack.Screen name="Register" component={RegisterScreen} />
            </>
        )}
      </Stack.Navigator>
  );
};

export default function App() {
  return (
      <SafeAreaProvider>
        <UserProvider>
          <NavigationContainer>
            <RootNavigator />
          </NavigationContainer>
        </UserProvider>
      </SafeAreaProvider>
  );
}