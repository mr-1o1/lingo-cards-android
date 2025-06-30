from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from googletrans import Translator
import asyncio

app = FastAPI()

# Add CORS middleware to allow requests from Android app
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # In production, specify your Android app's domain
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class TranslationRequest(BaseModel):
    sentence: str
    source_language: str = "en"
    target_language: str = "fi"

class TranslationResponse(BaseModel):
    translated_sentence: str

async def translate_sentence(sentence: str, source_language: str, target_language: str):
    try:
        translator = Translator()
        result = await translator.translate(sentence, src=source_language, dest=target_language)
        print(f"Translation result: {result}")  # Debug print
        return result
    except Exception as e:
        print(f"Translation error: {e}")  # Debug print
        raise e

@app.post("/translate", response_model=TranslationResponse)
async def translate(request: TranslationRequest):
    try:
        print(f"Received request: {request}")  # Debug print
        result = await translate_sentence(request.sentence, request.source_language, request.target_language)
        print(f"Result type: {type(result)}")  # Debug print
        print(f"Result attributes: {dir(result)}")  # Debug print
        
        if not result:
            raise HTTPException(status_code=500, detail="Translation returned None")
        
        # Check if result has text attribute
        if hasattr(result, 'text'):
            translated_text = result.text
        else:
            print(f"Available attributes: {dir(result)}")
            raise HTTPException(status_code=500, detail="Translation result has no text attribute")
        
        if not translated_text:
            raise HTTPException(status_code=500, detail="Translation returned empty text")
            
        return TranslationResponse(translated_sentence=translated_text)
    except Exception as e:
        print(f"Exception in translate endpoint: {e}")  # Debug print
        raise HTTPException(status_code=500, detail=f"Translation failed: {str(e)}")

@app.get("/")
async def root():
    return {"message": "LingoCards Translation API"}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
