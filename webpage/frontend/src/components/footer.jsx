import organizationLogo from '../assets/organization_logo.png'
import { useNavigate } from 'react-router-dom';

const Footer = () => {
    const navigate=useNavigate();
    const handleReleaseNotes = () =>{
        navigate('/home/releasenotes')
    }
    return (
        <div className="footer">
            <p className="footer-name">Email: info@grievance.ai</p>
            <p className="footer-name">Phone: +31 (0)20 3342686</p>
            <p className="footer-version" onClick={handleReleaseNotes} style={{ cursor: "pointer" }}>Version 1.0.3</p>
            <p className="footer-name">&copy; 2023 Grievance.ai. All rights reserved.</p>
        </div>
    )
}

export default Footer;