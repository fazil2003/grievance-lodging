import React from 'react';
import { useNavigate } from 'react-router-dom';
import organizationLogo from '../assets/organization_logo.png';
import { FaSignOutAlt, FaQuestionCircle } from 'react-icons/fa';
import { useTranslation } from "react-i18next";

const Header = () => {

    const { t } = useTranslation();

    const navigate = useNavigate();
    const handleLogout = () => {
        localStorage.removeItem("userid");
        navigate("/login");
    };

    const handleHelp = () => {
        navigate('/home/faq')
    };

    const headerStyle = {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        paddingRight: '16px',
    };

    const logoutIconStyle = {
        marginRight: '8px',
    };
    const helpIconStyle = {
        marginRight: '8px'
    }

    return (
        <div className="header" style={headerStyle}>
            <img src={organizationLogo} alt="Organization Logo" />
            <button className="help-button" onClick={handleHelp} >
                <FaQuestionCircle style={helpIconStyle} />
                { t('help') }
            </button>
            <button className='logout-button' onClick={handleLogout}>
                <FaSignOutAlt style={logoutIconStyle} />
                { t('logout') }
            </button>
        </div>
    );
};

export default Header;