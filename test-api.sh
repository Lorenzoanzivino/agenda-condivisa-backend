#!/bin/bash
# 1. Login e estrazione automatica del token con 'jq' (se installato) o grep
echo "Eseguo Login..."
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email": "lorenzo@test.it", "password": "passwordSicura123"}')

TOKEN=$(echo $LOGIN_RESPONSE | grep -oP '(?<="token":")[^"]*')

if [ -z "$TOKEN" ]; then
    echo "Errore: Login fallito. Controlla le credenziali o se lo User Service è attivo."
    exit 1
fi

echo "Token ottenuto con successo."

# 2. Chiamata all'Agent
echo "Interrogo l'Agent Service..."
curl -i -X GET "http://localhost:8080/api/chat?message=Ciao!%20Verifica%20se%20persisto." \
  -H "Authorization: Bearer $TOKEN"