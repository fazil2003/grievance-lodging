import React from 'react';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from "react-router-dom";
import defaultVariables from '../variables/variables';

import './view-grievance.css';

const ViewGrievance = () => {

    let { id } = useParams();
    const [grievance, setGrievance] = useState([]);
    const [departmentOne, setDepartmentOne] = useState([]);
    const [departmentTwo, setDepartmentTwo] = useState([]);
    const [departmentThree, setDepartmentThree] = useState([]);
    const [grievanceStatus, setGrievanceStatus] = useState(null);

    useEffect(() => {
        axios.get(defaultVariables['backend-url'] + "grievance/view/get?id=" + id)
            .then((res) => {
                setGrievance(res.data[0]);
                if (res.data[0].grievanceStatus == 0){
                    setGrievanceStatus("Not assigned");
                }
                if (res.data[0].grievanceStatus == 1){
                    setGrievanceStatus("Assigned");
                }
                if (res.data[0].grievanceStatus == 2){
                    setGrievanceStatus("Fixed");
                }
                    
                setDepartmentOne(res.data[0].grievanceDepartment[0]);
                if (res.data[0].grievanceDepartment[1]){
                    setDepartmentTwo(res.data[0].grievanceDepartment[1]);
                }
                else{
                    setDepartmentTwo(null);
                }
                if (res.data[0].grievanceDepartment[2]){
                    setDepartmentThree(res.data[0].grievanceDepartment[2]);
                }
                else{
                    setDepartmentThree(null);
                }
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    return (
        <div className='activity-details'>
            <p className='heading-medium' >{ grievance.grievanceTitle }</p>
            <div className='all-details-div'>
                <div className='details-div'>
                    <p className='details-field'>Title: </p>
                    <p className='details-value'>{ grievance.grievanceTitle }</p>
                </div>
                <div className='details-div'>
                    <p className='details-field'>Description: </p>
                    <p className='details-value'>{ grievance.grievanceDescription }</p>
                </div>
                <div className='details-div'>
                    <p className='details-field'>Departments: </p>
                    <p className='details-value'>
                        {
                        departmentOne && <div className='details-div'>
                            <p className='details-field'>1 </p>
                            <p className='details-value'>{ departmentOne.departmentName }</p>
                        </div>
                        }
                        {
                        departmentTwo && <div className='details-div'>
                            <p className='details-field'>2 </p>
                            <p className='details-value'>{ departmentTwo.departmentName }</p>
                        </div>
                        }
                        {
                        departmentThree && <div className='details-div'>
                            <p className='details-field'>3 </p>
                            <p className='details-value'>{ departmentThree.departmentName }</p>
                        </div>
                        }
                    </p>
                </div>
            </div>
            <p className='heading-small'>Status</p>
            <center>
                <div className='activity-buttons'>
                    <p className='heading-medium'>{ grievanceStatus}</p> 
                </div>
            </center>
        </div>
    )
};

export default ViewGrievance;