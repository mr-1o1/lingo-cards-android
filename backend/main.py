from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from googletrans import Translator
import asyncio

app = FastAPI()

class TranslationRequest(BaseModel):
    sentence: str
    source_language: str
    target_language: str

class TranslationResponse(BaseModel):
    translated_sentence: str

async def translate_sentence(sentence: str, source_language: str, target_language: str):
    translator = Translator()
    result = await translator.translate(sentence, src=source_language, dest=target_language)
    return result

@app.post("/translate", response_model=TranslationResponse)
async def translate(request: TranslationRequest):
    try:
        result = await translate_sentence(request.sentence, request.source_language, request.target_language)
        if not result or not hasattr(result, 'text'):
            raise HTTPException(status_code=500, detail="Translation failed or returned empty result.")
        return TranslationResponse(translated_sentence=result.text)
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Translation failed: {str(e)}")
