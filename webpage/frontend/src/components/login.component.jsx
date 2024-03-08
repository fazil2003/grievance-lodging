import React, {useState} from 'react';
import { useNavigate } from "react-router-dom";
import backgroundImage from '../assets/background.png'
import avatarProfileIcon from '../assets/avatar_profile_icon.png'
import passwordIcon from '../assets/password_icon.png'
import axios from 'axios';
import defaultVariables from './variables/variables';

const Login = (props) =>{

	const navigate = useNavigate();
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [role, setRole] = useState("admin");

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
                if(role === "admin"){
                    navigate("/home/dashboard");
                }
                else{
                    navigate("/home/dashboard");
                }
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

				<p className='heading' style={{ textAlign:'left' }}>Login</p>
				<br />

				<div className='login-categories'>
					<div id='category-admin' className='login-category' onClick={() => setRole("admin")}
					style={{ backgroundColor: role == "admin" ? 'lightblue': 'white', fontWeight: role == "admin" ? 'bold' :'normal',cursor: 'pointer'}}
					>
						<img src={ avatarProfileIcon } />
						<span>User</span>
					</div>
					<div id='category-staff' className='login-category' onClick={() => setRole("staff")}
					style={{ backgroundColor: role == "staff" ? 'lightblue': 'white', fontWeight: role == "staff" ? 'bold' :'normal', cursor: 'pointer'}}
					>
						<img src={ avatarProfileIcon } />
						<span>Officer</span>
					</div>
				</div>

				<div className='box'>
					<img src={ avatarProfileIcon } />
					<input type="text" placeholder='Username'  />
				</div>

				<div className='box'>
					<img src={ passwordIcon } />
					<input type="password" placeholder='Password'  />
				</div>

				<button className='form-button'>Log in</button>

				<br />
                {/* <div className='form-text'> */}
                    {/* <center>
                        Do you want to create new account? <a className='bold' href='/register'>Register</a>
                    </center> */}
                {/* </div> */}

			</form>

    	</div>
    )
    
}

export default Login;