from flask import Flask, jsonify, request 
from flask_cors import CORS, cross_origin
# MySQL
import MySQLdb

from ctransformers import AutoModelForCausalLM
from transformers import AutoTokenizer, pipeline
from keybert.llm import TextGeneration
from keybert import KeyLLM
import pandas as pd
import numpy as np

app = Flask(__name__)
CORS(app, support_credentials=True)

db = MySQLdb.connect("localhost", "root", "", "lodging_grievance")
cursor = db.cursor()

government_departments_map = {
    1: "Police department",
    2: "Transportation department",
    3: "Education department",
    4: "Health department",
    5: "Social services department",
    6: "Environmental protection department",
    7: "Housing and urban development department",
    8: "Labor welfare department",
    9: "Agriculture development department"
}

@app.route("/")
@cross_origin(supports_credentials=True)
def home():
    return "Hello, World!"

@app.route("/people/add", methods = ['POST'])
@cross_origin(supports_credentials=True)
def addPeople():
    request_data = request.get_json()
    personName = request_data['person_name']
    personEmail = request_data['person_email']
    personPassword = request_data['person_password']
    personAadhaar = request_data['person_aadhaar']
    personPhone = request_data['person_phone']
    # %d, %s, %f
    sql = "INSERT INTO person (person_name, person_email, person_password, person_aadhaar, person_phone) VALUES ('" + personName + "', '" + personEmail + "', '" + personPassword + "', '" + personAadhaar + "', '" + personPhone + "')"
    print(sql)
    cursor.execute(sql)
    db.commit()
    return "data inserted successfully"

@app.route("/grievance/add", methods = ['POST'])
@cross_origin(supports_credentials=True)
def addGrievance():
    request_data = request.get_json()
    grievanceTitle = request_data['grievance_title']
    grievanceDescription = request_data['grievance_description']
    grievancePerson = request_data['grievance_person']

    keywords = generateKeywords(grievanceDescription)

    print("KEYWORDS")
    print(keywords)
    import generate_accuracies as ga
    # Get the top 3 values.
    accuracies = ga.get_overall_accuracies(keywords)[:3]
    resultIndex = ""
    resultDepartments = ""
    # Add the index to result values
    if(len(accuracies) > 0):
        resultDepartments = "Grievance successfully lodged to "
    index = 0
    for listIndex, score in accuracies:
        resultIndex = resultIndex + str(listIndex) + " "
        if (index == 0):
            resultDepartments = resultDepartments + government_departments_map[listIndex] 
        else:
            resultDepartments = resultDepartments + ", " + government_departments_map[listIndex] 
        index += 1

    grievanceDepartments = resultIndex
    # %d, %s, %f
    sql = "INSERT INTO grievance (grievance_title, grievance_description, grievance_person, grievance_department) VALUES ('" + grievanceTitle + "', '" + grievanceDescription + "', " + grievancePerson + ", '" + grievanceDepartments + "')"
    # print(sql)
    cursor.execute(sql)
    db.commit()
    # print(result)
    return resultDepartments

def generateKeywords(sentence):

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

    prompt = """
I have the following document:
[DOCUMENT]

Based on the information above, extract the keywords that best describe the topic of the text.
Make sure to only extract keywords that appear in the text.
Use the following format separated by commas:
<keywords>
"""

    # Load it in KeyLLM
    llm = TextGeneration(generator, prompt=prompt)
    kw_model = KeyLLM(llm)

    documents = [
    sentence
    ]
    keywords = kw_model.extract_keywords(documents, check_vocab=True)

    return keywords[0]

@app.route('/grievance/get', methods = ['GET']) 
@cross_origin(supports_credentials=True)
def getGrievances(): 
    userID = request.args['userid']
    sql = "SELECT * FROM grievance WHERE grievance_person = " + userID + ""
    # print(sql)
    cursor.execute(sql)
    allGrievances = cursor.fetchall()

    data = []
    for grievance in allGrievances:
        grievanceID = grievance[0]
        grievanceTitle = grievance[1]
        grievanceDescription = grievance[2]
        grievancePerson = grievance[3]
        grievanceDepartment = grievance[4] # This contains ID
        grievanceDepartmentText = grievance[5] # This contains full text
        grievanceDate = grievance[6]
        grievanceStatus = grievance[7]

        obj = {
            'grievanceID': grievanceID,
            'grievanceTitle': grievanceTitle,
            'grievanceDescription': grievanceDescription,
            'grievancePerson': grievancePerson,
            'grievanceDepartment': grievanceDepartmentText,
            'grievanceDate': grievanceDate,
            'grievanceStatus': grievanceStatus
        }
        data.append(obj)

    print(data)

    return jsonify(data)
    
@app.route('/login', methods = ['POST']) 
@cross_origin(supports_credentials=True)
def login(): 
    request_data = request.get_json()
    username = request_data['username']
    password = request_data['password']
    role = request_data['role']

    sql = "SELECT * FROM person WHERE person_email = '" + username + "' AND person_password = '" + password + "'"
    # print(sql)
    cursor.execute(sql)
    myresult = cursor.fetchall()
    personID = 0
    for x in myresult:
        personID = x[0]

    data = { 
        "auth" : "success", 
        "userid": personID
    } 

    return jsonify(data)
    
if __name__ == "__main__":
    app.run(debug=True)