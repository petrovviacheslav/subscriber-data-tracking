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
       "totalTime": "192:46:06"
   },
   "outgoingCall": {
       "totalTime": "184:43:58"
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
       "totalTime": "13:20:28"
   },
   "outgoingCall": {
       "totalTime": "18:51:04"
   }
   },
   {
   "msisdn": "79876543221",
   "incomingCall": {
       "totalTime": "12:58:48"
   },
   "outgoingCall": {
       "totalTime": "16:18:24"
   }
   },
   {
   "msisdn": "79992221122",
   "incomingCall": {
       "totalTime": "23:09:29"
   },
   "outgoingCall": {
       "totalTime": "16:51:14"
   }
   },
   {
   "msisdn": "79123456789",
   "incomingCall": {
       "totalTime": "15:44:22"
   },
   "outgoingCall": {
       "totalTime": "18:07:34"
   }
   },
   {
   "msisdn": "79995558888",
   "incomingCall": {
       "totalTime": "19:54:15"
   },
   "outgoingCall": {
       "totalTime": "11:26:05"
   }
   },
   {
   "msisdn": "79874443333",
   "incomingCall": {
       "totalTime": "18:35:32"
   },
   "outgoingCall": {
       "totalTime": "13:33:45"
   }
   },
   {
   "msisdn": "79997776666",
   "incomingCall": {
       "totalTime": "12:34:44"
   },
   "outgoingCall": {
       "totalTime": "13:46:59"
   }
   },
   {
   "msisdn": "79111112222",
   "incomingCall": {
       "totalTime": "12:47:02"
   },
   "outgoingCall": {
       "totalTime": "17:34:20"
   }
   },
   {
   "msisdn": "79993334444",
   "incomingCall": {
       "totalTime": "14:21:10"
   },
   "outgoingCall": {
       "totalTime": "16:24:40"
   }
   },
   {
   "msisdn": "79888885555",
   "incomingCall": {
       "totalTime": "15:45:18"
   },
   "outgoingCall": {
       "totalTime": "16:17:03"
   }
   }
   ]
   ```
   