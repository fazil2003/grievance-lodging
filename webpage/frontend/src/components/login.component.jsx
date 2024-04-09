import React, {useState} from 'react';
import { useNavigate } from "react-router-dom";
import backgroundImage from '../assets/background.png'
import avatarProfileIcon from '../assets/avatar_profile_icon.png'
import passwordIcon from '../assets/password_icon.png'
import axios from 'axios';
import defaultVariables from './variables/variables';
import { useTranslation } from "react-i18next";
import LanguageSelector from './language-selector';

const Login = (props) =>{

	const { t } = useTranslation();

	const navigate = useNavigate();
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [role, setRole] = useState("user");

	function userLogin(event){
		event.preventDefault();
		let username = event.target[0].value;
		let password = event.target[1].value;
		const parameters = { username : username, password : password, role : role };

		axios.post(defaultVariables['backend-url'] + 'login', parameters)
        .then((response) => {
			props.setIsAuthenticated(true);
            if (response.data.auth == "success"){
				localStorage.setItem("userid", response.data.userid);
				localStorage.setItem("admindept", response.data.admindept);
                if(role === "admin"){
                    navigate("/admin/dashboard");
                }
                else{
                    navigate("/home/dashboard");
                }
            }
			else{
				alert("failed");
			}
		})
		.catch(error => {
			alert(error)
		});
	}
	
    return (
        <div className='login-container'>

			<img className='login-background-image' src={ backgroundImage } />

			<form className='login-form' onSubmit={userLogin} >

				<p className='heading' style={{ textAlign:'left' }}>{ t('login') }</p>
				<br />

				<div className='login-categories'>
					<div id='category-admin' className='login-category' onClick={() => setRole("user")}
					style={{ backgroundColor: role == "user" ? 'lightblue': 'white', fontWeight: role == "user" ? 'bold' :'normal',cursor: 'pointer'}}
					>
						<img src={ avatarProfileIcon } />
						<span>{ t('user') }</span>
					</div>
					<div id='category-staff' className='login-category' onClick={() => setRole("admin")}
					style={{ backgroundColor: role == "admin" ? 'lightblue': 'white', fontWeight: role == "admin" ? 'bold' :'normal', cursor: 'pointer'}}
					>
						<img src={ avatarProfileIcon } />
						<span>{ t('admin') }</span>
					</div>
				</div>

				<div className='box'>
					<img src={ avatarProfileIcon } />
					<input type="text" placeholder={ t('username') }  />
				</div>

				<div className='box'>
					<img src={ passwordIcon } />
					<input type="password" placeholder={ t('password') }  />
				</div>

				<button className='form-button'>{ t('login') }</button>

				<br />
                {/* <div className='form-text'> */}
                    {/* <center>
                        Do you want to create new account? <a className='bold' href='/register'>Register</a>
                    </center> */}
                {/* </div> */}

			</form>

			<div className='language-selector-login'>
				<p>Language</p>
				<LanguageSelector />
			</div>

    	</div>
    )
    
}

export default Login;