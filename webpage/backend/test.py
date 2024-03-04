from ctransformers import AutoModelForCausalLM
from transformers import AutoTokenizer, pipeline
from keybert.llm import TextGeneration
from keybert import KeyLLM
from sentence_transformers import SentenceTransformer
import pandas as pd
import numpy as np

def getKeywords():
    # Set gpu_layers to the number of layers to offload to GPU. Set to 0 if no GPU acceleration is available on your system.
    model = AutoModelForCausalLM.from_pretrained(
        "TheBloke/Mistral-7B-Instruct-v0.1-GGUF",
        model_file="mistral-7b-instruct-v0.1.Q4_K_M.gguf",
        model_type="mistral",
        gpu_layers=0,
        hf=True
    )

    # Tokenizer
    tokenizer = AutoTokenizer.from_pretrained("mistralai/Mistral-7B-Instruct-v0.1")

    # Pipeline
    generator = pipeline(
        model=model, tokenizer=tokenizer,
        task='text-generation',
        max_new_tokens=50,
        repetition_penalty=1.1
    )

    prompt = """I have the following document:
[DOCUMENT]

Based on the information above, extract the keywords that best describe the topic of the text.
Make sure to only extract keywords that appear in the text.
Use the following format separated by commas:
<keywords>
"""

    # Load it in KeyLLM
    llm = TextGeneration(generator, prompt=prompt)
    kw_model = KeyLLM(llm)

    sentence = "Strong punishment can prevent a lot of crimes in future. Let this be the one for minor driving."

    documents = [
    sentence
    ]

    # Extract embeddings
    # model = SentenceTransformer('all-MiniLM-L6-v2')
    # embeddings = model.encode(documents, convert_to_tensor=True)

    # keywords = kw_model.extract_keywords(documents, check_vocab=True, embeddings=embeddings, threshold=.75)
    keywords = kw_model.extract_keywords(documents, check_vocab=True)
    keywords_list = keywords[0]
    print(keywords_list)

getKeywords()