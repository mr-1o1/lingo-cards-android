# LingoCards Backend

This is the backend API server for the LingoCards Android application.

## Setup

1. **Install Python dependencies:**
   ```bash
   pip install -r requirements.txt
   ```

2. **Start the server:**
   ```bash
   python start_server.py
   ```
   
   Or alternatively:
   ```bash
   uvicorn main:app --host 0.0.0.0 --port 8000 --reload
   ```

## API Endpoints

- `GET /` - Health check endpoint
- `POST /translate` - Translate text

### Translation Request Format
```json
{
  "sentence": "Hello world",
  "source_language": "en",
  "target_language": "fi"
}
```

### Translation Response Format
```json
{
  "translated_sentence": "Hei maailma"
}
```

## Connecting from Android

- **For Android Emulator:** The Android app is configured to use `http://10.0.2.2:8000/`
- **For Physical Device:** Update the `BASE_URL` in `ApiClient.kt` to use your computer's IP address (e.g., `http://192.168.1.100:8000/`)

## Troubleshooting

1. **CORS Issues:** The server includes CORS middleware to allow requests from the Android app
2. **Network Issues:** Make sure your Android device/emulator can reach the backend server
3. **Translation Failures:** The backend uses Google Translate API, so internet connectivity is required

## Development

The server runs on FastAPI with automatic API documentation available at:
- http://localhost:8000/docs
- http://localhost:8000/redoc 