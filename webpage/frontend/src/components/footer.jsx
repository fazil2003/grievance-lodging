import organizationLogo from '../assets/organization_logo.png'
import { useNavigate } from 'react-router-dom';
import { useTranslation } from "react-i18next";

const Footer = () => {

    const { t } = useTranslation();

    const navigate=useNavigate();
    const handleReleaseNotes = () =>{
        navigate('/home/releasenotes')
    }
    return (
        <div className="footer">
            <p className="footer-name">{ t('email_info') }</p>
            <p className="footer-name">{ t('phone_info') }</p>
            <p className="footer-version" onClick={handleReleaseNotes} style={{ cursor: "pointer" }}>{ t('version_info') }</p>
            <p className="footer-name">&copy; { t('copyright_info') }</p>
        </div>
    )
}

export default Footer;