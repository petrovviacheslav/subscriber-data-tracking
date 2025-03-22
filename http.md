# Примеры http-запросов

1. Запрос:
   ```
   POST http://localhost:8080/api/cdr-reports?msisdn=79876543221&start=2025-01-01T00:00:00&end=2025-01-15T23:59:59
   ```
   Ответ:
   ```http request
   HTTP/1.1 202 
   Headers
   
   {
   "status": "IN_PROGRESS",
   "requestId": "1e3e8126-2157-470f-aefa-cc99fd64afc0",
   "message": "Report generation started"
   }
   ```

2. Запрос:
   ```
   GET http://localhost:8080/api/udr/by-msisdn?msisdn=79996667755
   ```
   Ответ:
   ```http request
   HTTP/1.1 200 
   Headers
   
   {
   "msisdn": "79996667755",
   "incomingCall": {
       "totalTime": "00:00:00"
   },
   "outgoingCall": {
       "totalTime": "00:00:00"
   }
   }
   ```

3. Запрос:
   ```
   GET http://localhost:8080/api/udr/all?year=2025&month=4
   ```
   Ответ:
   ```http request
   HTTP/1.1 200 
   Headers
      
   [
   {
   "msisdn": "79996667755",
   "incomingCall": {
       "totalTime": "00:00:00"
   },
   "outgoingCall": {
        "totalTime": "00:00:00"
   }
   },
   {
   "msisdn": "79876543221",
   "incomingCall": {
       "totalTime": "00:00:00"
   },
   "outgoingCall": {
       "totalTime": "00:00:00"
   }
   },
   {
   "msisdn": "79992221122",
   "incomingCall": {
       "totalTime": "00:00:00"
   },
   "outgoingCall": {
       "totalTime": "00:00:00"
   }
   },
   {
   "msisdn": "79123456789",
   "incomingCall": {
       "totalTime": "00:00:00"
   },
   "outgoingCall": {
       "totalTime": "00:00:00"
   }
   },
   {
   "msisdn": "79995558888",
   "incomingCall": {
       "totalTime": "00:00:00"
   },
   "outgoingCall": {
       "totalTime": "00:00:00"
   }
   },
   {
   "msisdn": "79874443333",
   "incomingCall": {
       "totalTime": "00:00:00"
   },
   "outgoingCall": {
       "totalTime": "00:00:00"
   }
   },
   {
   "msisdn": "79997776666",
   "incomingCall": {
       "totalTime": "00:00:00"
   },
   "outgoingCall": {
       "totalTime": "00:00:00"
   }
   },
   {
   "msisdn": "79111112222",
   "incomingCall": {
       "totalTime": "00:00:00"
   },
   "outgoingCall": {
       "totalTime": "00:00:00"
   }
   },
   {
   "msisdn": "79993334444",
   "incomingCall": {
       "totalTime": "00:00:00"
   },
   "outgoingCall": {
       "totalTime": "00:00:00"
   }
   },
   {
   "msisdn": "79888885555",
   "incomingCall": {
       "totalTime": "00:00:00"
   },
   "outgoingCall": {
       "totalTime": "00:00:00"
   }
   }
   ]
   ```
   