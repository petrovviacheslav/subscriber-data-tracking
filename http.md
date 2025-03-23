# Примеры http-запросов

1. Запрос:
   ```
   POST http://localhost:8080/api/cdr-reports?msisdn=79876543221&start=2025-01-01T00:00:00&end=2025-01-15T23:59:59
   ```
   Ответ:
   ```http request
   HTTP/1.1 200 
   Headers

   {
   "message": "Report created",
   "requestId": "ea97433f-8b77-4c55-adf2-fef55f95e2cc",
   "status": "SUCCESSFUL"
   }
   ```
   Сгенерированный отчёт находится в [reports/](./reports)
2. Запрос (с необслуживаемым абонентом):
   ```
   POST http://localhost:8080/api/cdr-reports?msisdn=79876543000&start=2025-01-01T00:00:00&end=2025-01-15T23:59:59
   ```
   Ответ:
   ```http request
   HTTP/1.1 200 
   Headers

   {
   "message": "Subscriber not found",
   "requestId": "2bf30c46-5294-42fe-ad26-f12fc6efb65a",
   "status": "ERROR"
   }
   ```
3. Запрос (с необслуживаемым абонентом):
   ```
   GET http://localhost:8080/api/udr/by-msisdn?msisdn=79996667700
   ```
   Ответ:
   ```http request
   HTTP/1.1 400 
   Headers
   
   {
   "message": "Subscriber not found"
   }
   ```
   
4. Запрос:
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
5. Запрос (с неправильным месяцем):
   ```http request
   GET http://localhost:8080/api/udr/all?year=2025&month=24
   ```
   Ответ:
   ```
   HTTP/1.1 400 
   Headers

   {
   "status": "ERROR"
   }
   ```
6. Запрос:
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
           "totalTime": "16:41:14"
       },
       "outgoingCall": {
           "totalTime": "22:31:10"
       }
   },
   {
       "msisdn": "79876543221",
       "incomingCall": {
           "totalTime": "20:53:24"
       },
       "outgoingCall": {
           "totalTime": "22:12:08"
       }
   },
   {
       "msisdn": "79992221122",
       "incomingCall": {
           "totalTime": "23:59:36"
       },
       "outgoingCall": {
           "totalTime": "21:25:11"
       }
   },
   {
       "msisdn": "79123456789",
       "incomingCall": {
           "totalTime": "22:13:59"
       },
       "outgoingCall": {
           "totalTime": "14:15:39"
       }
   },
   {
       "msisdn": "79995558888",
       "incomingCall": {
           "totalTime": "11:14:59"
       },
       "outgoingCall": {
           "totalTime": "10:28:20"
       }
   },
   {
       "msisdn": "79874443333",
       "incomingCall": {
           "totalTime": "15:19:03"
       },
       "outgoingCall": {
           "totalTime": "12:35:25"
       }
   },
   {
       "msisdn": "79997776666",
       "incomingCall": {
           "totalTime": "19:08:48"
       },
       "outgoingCall": {
           "totalTime": "18:06:01"
       }
   },
   {
       "msisdn": "79111112222",
       "incomingCall": {
           "totalTime": "15:45:37"
       },
       "outgoingCall": {
           "totalTime": "16:33:15"
       }
   },
   {
       "msisdn": "79993334444",
       "incomingCall": {
           "totalTime": "19:16:01"
       },
       "outgoingCall": {
           "totalTime": "24:43:27"
       }
   },
   {
       "msisdn": "79888885555",
       "incomingCall": {
           "totalTime": "14:35:56"
       },
       "outgoingCall": {
           "totalTime": "16:18:01"
       }
   }
   ]
   ```