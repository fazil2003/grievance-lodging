import React, {useState} from 'react';
import { useNavigate } from "react-router-dom";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom"

import Header from './header';
import Footer from './footer';
import SideBar from './sidebar';

import AdminViewGrievance from './admin/admin-view-grievance.component';
import AdminDashboard from './admin/admin-dashboard.component';

const Admin = () =>{
	const navigate = useNavigate();
    return (
        <>
        <Header />
        <div className='outer-container'>
            <SideBar />
            <div className='inner-container'>
            <Routes>
                <Route exact path="/" element={<AdminDashboard />} />
                <Route exact path="/dashboard" element={<AdminDashboard />} />
                <Route exact path="/grievance/view/:id/:status" element={<AdminViewGrievance />} />
                <Route exact path="*" element={<AdminDashboard />} />
            </Routes>
            </div>
        </div>
        <Footer />
        </>
    )
}
export default Admin;