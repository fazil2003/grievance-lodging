import keywords_departments_file as kdf
departments = kdf.get_department_keywords()

# For Multiple Departments
from Levenshtein import ratio

# SpaCy
import spacy
nlp = spacy.load("en_core_web_lg")

def get_accuracies_levenshtein(keywords):
    relevance = []
    # Levenshtein
    final_accuracies = []
    departmentID = 1
    for each_department in departments:
        relevance = []
        for keyword_department in each_department:
            for keyword in keywords:
                relevance.append(round(ratio(keyword_department, keyword) * 100, 2))
        relevance.sort(reverse = True)
        final_accuracies.append([departmentID, relevance[0]])
        departmentID += 1
    final_accuracies.sort(key = lambda x: x[1], reverse = True)
    print(final_accuracies)

def get_accuracies_spacy(keywords):
    
    # For Multiple Departments
    relevance = []
    # Levenshtein
    keywords = ["the employees are punished severly"]
    final_accuracies = []
    departmentID = 1
    for each_department in departments:
        relevance = []
        for keyword_department in each_department:
            for keyword in keywords[0]:
                # print(keyword_department, keyword)
                doc1 = nlp(keyword_department)
                doc2 = nlp(keyword)
                relevance.append(round((doc1.similarity(doc2)) * 100, 2))
        relevance.sort(reverse = True)
        # print(relevance)
        final_accuracies.append([departmentID, relevance[0]])
        departmentID += 1
    final_accuracies.sort(key = lambda x: x[1], reverse = True)
    print(final_accuracies)

keywords = ['minor driving', 'crimes', 'future']
print("Levenshtein")
get_accuracies_levenshtein(keywords)
print("Spacy")
get_accuracies_spacy(keywords)