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

    import generate_accuracies as ga
    # Get the top 3 values.
    accuracies = ga.get_overall_accuracies(keywords)[:3]
    resultIndex = ""
    resultDepartments = ""
    # Add the index to result values
    if(len(accuracies) > 0):
        resultDepartments = "Grievance successfully lodged to "
    get_top_department_indexes = []
    index = 0
    for listIndex, score in accuracies:
        get_top_department_indexes.append(listIndex)
        resultIndex = resultIndex + str(listIndex) + " "
        if (index == 0):
            resultDepartments = resultDepartments + government_departments_map[listIndex] 
        else:
            resultDepartments = resultDepartments + ", " + government_departments_map[listIndex] 
        index += 1
    grievanceDepartments = resultIndex

    get_top_department_indexes = [1, 8, 5]
    get_the_department_id_to_post = []
    for individual_department_index in get_top_department_indexes:
        sql = "SELECT * FROM departments WHERE department_category = " + str(individual_department_index)
        cursor.execute(sql)
        query_all_departments = cursor.fetchall()
        data = []
        obj = {}
        minimum_distance = float('inf')
        minimum_distance_department_id = 0
        for department_to_complaint in query_all_departments:
            department_id = department_to_complaint[0]
            department_name = department_to_complaint[1]
            department_category = department_to_complaint[2]
            department_location = department_to_complaint[3]
            distance_between_locations = get_distance(department_location)
            if (distance_between_locations < minimum_distance):
                minimum_distance = distance_between_locations
                minimum_distance_department_id = department_id
                obj = {
                    'departmentID': department_id,
                    'departmentName': department_name,
                    'departmentCategory': department_category,
                    'departmentLocation': department_location
                }
        get_the_department_id_to_post.append(str(minimum_distance_department_id))
    string_departments_to_post = " ".join(get_the_department_id_to_post)
    # %d, %s, %f
    sql = "INSERT INTO grievance (grievance_title, grievance_description, grievance_person, grievance_department) VALUES ('" + grievanceTitle + "', '" + grievanceDescription + "', " + grievancePerson + ", '" + string_departments_to_post + "')"
    cursor.execute(sql)
    db.commit()
    return resultDepartments

# Extract the keywords as a list for the given sentence.
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

# Return the distance between the current location and the parameter location.
def get_distance(location):
    # importing geopy library
    from geopy.distance import geodesic as GD
    from geopy.geocoders import Nominatim
    import geocoder
    # Get the current location of the user.
    g = geocoder.ip('me')
    current_location = g.latlng #list
    # calling the Nominatim tool
    loc = Nominatim(user_agent="GetLoc")
    # entering the location name
    getLoc = loc.geocode(location)
    location_1 = (current_location[0], current_location[1])
    location_2 = (getLoc.latitude, getLoc.longitude)
    distance_between_locations = GD(location_1, location_2)
    return distance_between_locations

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
            'grievanceDepartment': grievanceDepartment,
            'grievanceDate': grievanceDate,
            'grievanceStatus': grievanceStatus
        }
        data.append(obj)

    print(data)

    return jsonify(data)
    

@app.route('/grievance/view/get', methods = ['GET'])
@cross_origin(supports_credentials= True)
def get_individual_grievance():
    grievance_id = request.args['id']
    # Get the grievance based on grievance_id.
    sql = "SELECT * FROM grievance WHERE grievance_id = " + grievance_id + ""
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

        # Get the department details of which the grievance has been lodged
        data_department = []
        grievance_departments_list = grievanceDepartment.split()
        for grievance_department_id in grievance_departments_list:
            # Get the departments based on id.
            sql_department = "SELECT * FROM departments WHERE department_id = " + grievance_department_id + ""
            cursor.execute(sql_department)
            get_department = cursor.fetchall()

            for department in get_department:

                department_id = department[0]
                department_name = department[1]
                department_category = department[2]
                department_location = department[3]
                department_keywords = department[4] 

                obj = {
                    'departmentID': department_id,
                    'departmentName': department_name,
                    'departmentCategory': department_category,
                    'departmentLocation': department_location,
                    'departmentKeywords': department_keywords
                }
                data_department.append(obj)

        obj = {
            'grievanceID': grievanceID,
            'grievanceTitle': grievanceTitle,
            'grievanceDescription': grievanceDescription,
            'grievancePerson': grievancePerson,
            'grievanceDepartment': data_department,
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