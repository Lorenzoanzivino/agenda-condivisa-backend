export const Texts = {
    auth: {
        loginTitle: "Bentornato!",
        loginSubtitle: "Accedi per gestire i tuoi impegni",
        registerTitle: "Crea Account",
        emailPlaceholder: "Inserisci la tua email",
        passwordPlaceholder: "Password",
    },
    home: {
        welcome: (name: string) => `Ciao, ${name}!`,
        subtitle: "Ecco i tuoi impegni di oggi",
        logout: "Esci",
        noEvents: "Non hai eventi in programma per oggi.",
    },
    errors: {
        generic: "Qualcosa è andato storto.",
        unauthorized: "Sessione scaduta, effettua di nuovo il login.",
    }
};