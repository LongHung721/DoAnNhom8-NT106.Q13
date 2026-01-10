# NetCafe Backend (Spring Boot + Firestore) – Refactored

This backend is refactored to match your existing Java Swing frontend calls.

## ✅ Matches your frontend
- `POST http://localhost:8080/api/auth/login`
  - Request JSON: `{ "username": "...", "password": "..." }`
  - Success (200): `{ "token": "<jwt>" }`
  - Fail (401): `{ "error": { "message": "Invalid credentials" } }`
- `POST http://localhost:8080/api/auth/reset-password`
  - Request JSON: `{ "idCard": "...", "newPassword": "..." }`
  - Success (200): `{ "status": "OK" }`

## Firestore collection used for auth
Default: `Khach_Hang` (same as your old server code)
Fields expected:
- `TenTK` (username)
- `MatKhau` (bcrypt hash)
- `LoaiKH` (role, optional)
- `SoCCCD` (for reset-password lookup)

You can override via env:
- `APP_AUTH_COLLECTION=Khach_Hang`

## Setup Firebase credential
Option A (recommended): environment variable
- Windows PowerShell:
  `$env:GOOGLE_APPLICATION_CREDENTIALS="C:\path\serviceAccountKey.json"`
- macOS/Linux:
  `export GOOGLE_APPLICATION_CREDENTIALS=/path/serviceAccountKey.json`

Option B: set absolute path
- `FIREBASE_SERVICE_ACCOUNT_PATH=/absolute/path/serviceAccountKey.json`

## Run in IntelliJ
1. Open folder `netcafe-backend` as Maven project
2. Run `com.netcafe.backend.NetCafeApplication`

## Notes
- Other endpoints included for future features:
  - `GET /api/foods`
  - `POST /api/orders`, `GET /api/orders/pending`, `PUT /api/orders/{id}/fulfill`
  - `GET/POST /api/chat/messages`
  - WebSocket STOMP endpoint: `/ws`
    - subscribe: `/topic/chat`, `/topic/orders`
    - send: `/app/chat.send`
