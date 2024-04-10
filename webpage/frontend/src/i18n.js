import i18n from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import { initReactI18next } from 'react-i18next';

i18n
.use(LanguageDetector)
.use(initReactI18next).init({
    debug: true,
    lng: "en",
    resources: {
        en: {
            translation: {
                greeting: "Hello, Welcome!",
                login: "Login",
                user: "User",
                admin: "Admin",
                username: "Username",
                password: "Password",
                add_grievance: "Add Grievance",
                export_as_csv: "Export as CSV",
                dashboard: "All Grievances",
                email_info: "Email: info@grievance.ai",
                phone_info: "Phone: +31 (0)20 3342686",
                version_info: "Version 1.0.3",
                copyright_info: "2024 Grievance.ai. All rights reserved.",
                title: "Title of the grievance",
                description: "Description of the grievance",
                submit: "Submit",
                help: "Help",
                logout: "Logout",
                departments: "Departments",
                status: "Status",
                grievance_id: "Grievance ID",
                grievance_title: "Grievance Title",
                grievance_description: "Grievance Description",
                grievance_department: "Grievance Department",
                grievance_date: "Grievance Date",
                grievance_status: "Grievance Status"
            },
        },
        hi: {
            translation: {
                greeting: "नमस्ते, स्वागत है!",
                login: "लॉग इन करें",
                user: "उपयोगकर्ता",
                admin: "व्यवस्थापक",
                username: "उपयोगकर्ता नाम",
                password: "पासवर्ड",
                add_grievance: "शिकायत जोड़ें",
                export_as_csv: "सीएसवी के रूप में निर्यात करें",
                dashboard: "सभी शिकायतें",
                email_info: "ईमेल: info@grievance.ai",
                phone_info: "फ़ोन: +31 (0)20 3342686",
                version_info: "संस्करण 1.0.3",
                copyright_info: "2024 Grievance.ai. सर्वाधिकार सुरक्षित।",
                title: "शिकायत का शीर्षक",
                description: "शिकायत का विवरण",
                submit: "जमा करना",
                help: "मदद",
                logout: "लॉग आउट",
                departments: "विभागों",
                status: "स्थिति",
                grievance_id: "शिकायत आईडी",
                grievance_title: "शिकायत शीर्षक",
                grievance_description: "शिकायत विवरण",
                grievance_department: "शिकायत विभाग",
                grievance_date: "शिकायत दिनांक",
                grievance_status: "शिकायत की स्थिति"
            }
        },
        ta: {
            translation: {
                greeting: "வணக்கம் வருக!",
                login: "உள்நுழைய",
                user: "பயனர்",
                admin: "நிர்வாகம்",
                username: "பயனர் பெயர்",
                password: "கடவுச்சொல்",
                add_grievance: "குறையைச் சேர்க்கவும்",
                export_as_csv: "CSV ஆக ஏற்றுமதி செய்யவும்",
                dashboard: "அனைத்து குறைகளும்",
                email_info: "மின்னஞ்சல்: info@grievance.ai",
                phone_info: "தொலைபேசி: +31 (0)20 3342686",
                version_info: "பதிப்பு 1.0.3",
                copyright_info: "2024 Grievance.ai. அனைத்து உரிமைகளும் பாதுகாக்கப்பட்டவை.",
                title: "குறையின் தலைப்பு",
                description: "குறையின் விளக்கம்",
                submit: "சமர்ப்பிக்கவும்",
                help: "உதவி",
                logout: "வெளியேறு",
                departments: "துறைகள்",
                status: "நிலை",
                grievance_id: "புகார் ஐடி",
                grievance_title: "புகார் தலைப்பு",
                grievance_description: "குறை விளக்கம்",
                grievance_department: "குறைதீர்க்கும் துறை",
                grievance_date: "புகார் தேதி",
                grievance_status: "புகார் நிலை"
            }
        }
    }
});