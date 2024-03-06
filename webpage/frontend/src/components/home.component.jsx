import React, {useState} from 'react';
import { useNavigate } from "react-router-dom";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom"

import Header from './header';
import Footer from './footer';
import SideBar from './sidebar';
import Dashboard from './dashboard/dashboard.component';
import AddPeopleForm from './people/form.component';
import AddGrievanceForm from './grievance/form.component';
import ViewGrievance from './grievance/view-grievance.component';
import SelectGrievance from './grievance/select-grievance.component';

const Home = () =>{
	const navigate = useNavigate();
    return (
        <>
        <Header />
        <div className='outer-container'>
            <SideBar />
            <div className='inner-container'>
            <Routes>
                <Route exact path="/" element={<Dashboard />} />
                <Route exact path="/dashboard" element={<Dashboard />} />
                <Route exact path="/people/add" element={<AddPeopleForm />} />
                <Route exact path="/grievance/add" element={<AddGrievanceForm />} />
                <Route exact path="/grievance/select/:id" element={<SelectGrievance />} />
                <Route exact path="/grievance/view/:id" element={<ViewGrievance />} />
                <Route exact path="*" element={<Dashboard />} />
            </Routes>
            </div>
        </div>
        <Footer />
        </>
    )
}
export default Home;