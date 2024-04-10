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

# for language translation.
import os
import json
from deep_translator import GoogleTranslator

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
def add_people():
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
def add_grievance():
    request_data = request.get_json()
    grievanceTitle = request_data['grievance_title']
    grievanceDescription = request_data['grievance_description']
    grievancePerson = request_data['grievance_person']

    print("Original Description", grievanceDescription)

    # Translate the langauge.
    translator = GoogleTranslator(source='auto', target='en')
    # import time
    # time.sleep(2)
    translated_description = translator.translate(grievanceDescription)

    print("Translated Description", translated_description)

    # Pass the translated grievance to generate the keywords.
    keywords = generate_keywords(translated_description)

    print("Generated Keywords", keywords)

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

    # For testing purpose.
    # get_top_department_indexes = [1, 8, 5]
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

    from datetime import date
    today = date.today()
    now = today.strftime('%Y-%m-%d')
    # %d, %s, %f
    sql = "INSERT INTO grievance (grievance_title, grievance_description, grievance_person, grievance_department, grievance_date) VALUES ('" + grievanceTitle + "', '" + grievanceDescription + "', " + grievancePerson + ", ' " + string_departments_to_post + " ', '" + now + "')"
    cursor.execute(sql)
    db.commit()

    sql = "SELECT * FROM grievance ORDER BY grievance_id DESC LIMIT 1"
    cursor.execute(sql)
    query_get_current_grievance = cursor.fetchall()
    grievance_id = ""
    for current_grievance in query_get_current_grievance:
        grievance_id = str(current_grievance[0])

    result = {
                'grievance_id': grievance_id
            }
    return result

@app.route("/grievance/update", methods = ['POST'])
@cross_origin(supports_credentials=True)
def update_grievance():
    request_data = request.get_json()
    grievance_id = request_data['grievance_id']
    grievance_option_1 = request_data['grievance_option_1']
    grievance_option_2 = request_data['grievance_option_2']
    grievance_option_3 = request_data['grievance_option_3']

    sql = "SELECT * FROM grievance WHERE grievance_id = " + grievance_id + ""
    cursor.execute(sql)
    get_grievance = cursor.fetchall()
    data = []
    grievance_department = ""
    for grievance in get_grievance:
        grievance_department = grievance[4]
        grievance_department = grievance_department.strip()
    grievance_department_list = grievance_department.split(" ")
    
    print(grievance_department_list)
    print("One", grievance_option_1)
    print("Two", grievance_option_2)
    print("Three", grievance_option_3)
    
    new_grievance_list = []
    if (grievance_option_1):
        new_grievance_list.append(str(grievance_department_list[0]))
    if (grievance_option_2):
        new_grievance_list.append(str(grievance_department_list[1]))
    if (grievance_option_3):
        new_grievance_list.append(str(grievance_department_list[2]))

    new_grievance_department = " ".join(new_grievance_list)
    sql = "UPDATE grievance SET grievance_department = ' " + new_grievance_department + " ' WHERE grievance_id = " + str(grievance_id) + ""
    cursor.execute(sql)
    db.commit()

    result = {
            'data': 'success'
        }
    return result

# Extract the keywords as a list for the given sentence.
def generate_keywords(sentence):

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
    print("\nCurrent Location: ", location)
    getLoc = loc.geocode(location)
    location_1 = (current_location[0], current_location[1])
    location_2 = (getLoc.latitude, getLoc.longitude)

    # Check for same state and same district.
    # initialize Nominatim API
    geolocator = Nominatim(user_agent="geoapiExercises")
    # Latitude, Longitude
    location_me = geolocator.reverse(str(current_location[0]) + "," + str(current_location[1]))
    location_them = geolocator.reverse(str(getLoc.latitude) + "," + str(getLoc.longitude))

    address_1 = location_me.raw['address']
    district_1 = address_1.get('state_district', '')
    state_1 = address_1.get('state', '')

    address_2 = location_them.raw['address']
    district_2 = address_2.get('state_district', '')
    state_2 = address_2.get('state', '')

    if (district_1 == district_2):
        return -1
    if (state_1 == state_2):
        return 0
    distance_between_locations = GD(location_1, location_2)
    return distance_between_locations

@app.route('/grievance/get', methods = ['GET']) 
@cross_origin(supports_credentials=True)
def get_grievances(): 
    userID = request.args['userid']
    if 'lang' in request.args:
        lang = request.args['lang']
    else:
        lang = 'en'
    print(lang)
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

        # Translate the langauge.
        translator = GoogleTranslator(source='auto', target=lang)
        translated_title = translator.translate(grievanceTitle)
        translated_description = translator.translate(grievanceDescription)

        obj = {
            'grievanceID': grievanceID,
            'grievanceTitle': translated_title,
            'grievanceDescription': translated_description,
            'grievancePerson': grievancePerson,
            'grievanceDepartment': grievanceDepartment,
            'grievanceDate': grievanceDate,
            'grievanceStatus': grievanceStatus
        }
        data.append(obj)

    print(data)

    return jsonify(data)

@app.route('/grievance/get.php', methods = ['GET']) 
@cross_origin(supports_credentials=True)
def get_grievances_php(): 
    userID = request.args['userid']
    start = request.args["start"]
    limit = request.args["limit"]
    sql = "SELECT * FROM grievance WHERE grievance_person = " + userID + " ORDER BY grievance_id LIMIT " + str(start) + ", " + str(limit)
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

        # Get the department details of which the grievance has been lodged
        count = 0
        departmentOne = ""
        departmentTwo = ""
        departmentThree = ""
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

                if (count == 0):
                    departmentOne = department_name
                if (count == 1):
                    departmentTwo = department_name
                if (count == 2):
                    departmentThree = department_name

                count += 1

        obj = {
            'grievanceID': grievanceID,
            'grievanceTitle': grievanceTitle,
            'grievanceDescription': grievanceDescription,
            'grievancePerson': grievancePerson,
            'grievanceDepartment': grievanceDepartment,
            'grievanceDate': grievanceDate,
            'grievanceStatus': grievanceStatus,
            'grievanceDepartmentOne': departmentOne,
            'grievanceDepartmentTwo': departmentTwo,
            'grievanceDepartmentThree': departmentThree
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

    if (role == 'admin'):
        sql = "SELECT * FROM admin WHERE admin_email = '" + username + "' AND admin_password = '" + password + "'"
    else:
        sql = "SELECT * FROM person WHERE person_email = '" + username + "' AND person_password = '" + password + "'"
    # print(sql)
    cursor.execute(sql)
    myresult = cursor.fetchall()
    personID = 0
    adminDept = 0
    for x in myresult:
        personID = x[0]
        if (role == 'admin'):
            adminDept = x[4]

    if(len(myresult) > 0):
        data = { 
            "auth" : "success", 
            "userid": personID,
            "admindept": adminDept
        } 
    else:
        data = { 
            "auth" : "fail", 
            "userid": "0"
        } 

    return jsonify(data)

@app.route('/admin/grievance/get', methods = ['GET']) 
@cross_origin(supports_credentials=True)
def admin_get_grievances(): 
    userID = request.args['userid']
    if 'lang' in request.args:
        lang = request.args['lang']
    else:
        lang = 'en'
    if (userID == '0'):
        sql = "SELECT * FROM grievance"
    else:
        sql = "SELECT * FROM grievance WHERE grievance_department LIKE '%" + userID + "%'"

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

        # Translate the langauge.
        translator = GoogleTranslator(source='auto', target=lang)
        translated_title = translator.translate(grievanceTitle)
        translated_description = translator.translate(grievanceDescription)
        print(translated_title)
        print(translated_description)
        obj = {
            'grievanceID': grievanceID,
            'grievanceTitle': translated_title,
            'grievanceDescription': translated_description,
            'grievancePerson': grievancePerson,
            'grievanceDepartment': grievanceDepartment,
            'grievanceDate': grievanceDate,
            'grievanceStatus': grievanceStatus
        }
        data.append(obj)

    print(data)

    return jsonify(data)

@app.route("/admin/grievance/update", methods = ['POST'])
@cross_origin(supports_credentials=True)
def admin_update_grievance():
    request_data = request.get_json()
    grievance_id = request_data['grievance_id']
    grievance_status = request_data['grievance_status']

    sql = "UPDATE grievance SET grievance_status = " + str(grievance_status) + " WHERE grievance_id = " + str(grievance_id) + ""
    cursor.execute(sql)
    db.commit()
    result = {
            'data': 'success'
        }
    return result
    
if __name__ == "__main__":
    app.run(debug=True, host= '0.0.0.0')