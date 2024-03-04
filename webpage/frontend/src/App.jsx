import { useState } from 'react'
import { BrowserRouter as Router, Route, Routes } from "react-router-dom"
import './App.css'
import Login from './components/login.component'
import Home from './components/home.component'

function App() {
  const [count, setCount] = useState(0);
  const [isAuthenticated, setIsAuthenticated] = useState(localStorage.getItem("userid"));

  return (
		<Router>
			<Routes>
				<Route exact path="/login" element={<Login setIsAuthenticated = {setIsAuthenticated}/>} />
				{ isAuthenticated ?
					(<Route exact path="/home/*" element={<Home setIsAuthenticated = {setIsAuthenticated} />} />):
					(<Route exact path="/home/*" element={<Login setIsAuthenticated = {setIsAuthenticated} />}  />)
				}
				{ isAuthenticated ?
					(<Route exact path="/staff/*" element={<Home setIsAuthenticated = {setIsAuthenticated} />} />):
					(<Route exact path="/staff/*" element={<Login setIsAuthenticated = {setIsAuthenticated} />}  />)
				}
				<Route path="*" element={<Login setIsAuthenticated = {setIsAuthenticated}/>} />
			</Routes>
		</Router>
  	);
}

export default App