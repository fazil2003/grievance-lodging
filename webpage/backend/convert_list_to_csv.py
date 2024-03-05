import keywords_departments_file as kdf
file = kdf.keywords_for_agriculture
for i in range(len(file)):
    print(file[i], end=", ")