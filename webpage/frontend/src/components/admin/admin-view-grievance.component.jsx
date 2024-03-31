import React from 'react';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from "react-router-dom";
import defaultVariables from '../variables/variables';
import { Select, Button, MenuItem, InputLabel, Alert, AlertTitle } from '@mui/material';

import '../grievance/view-grievance.css';

const AdminViewGrievance = () => {

    let { id, status } = useParams();
    const [grievance, setGrievance] = useState([]);
    const [departmentOne, setDepartmentOne] = useState([]);
    const [departmentTwo, setDepartmentTwo] = useState([]);
    const [departmentThree, setDepartmentThree] = useState([]);
    const [grievanceStatus, setGrievanceStatus] = useState(0);

    const [successMessage, setSuccessMessage] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    useEffect(() => {
        setGrievanceStatus(status);
        axios.get(defaultVariables['backend-url'] + "grievance/view/get?id=" + id)
            .then((res) => {
                setGrievance(res.data[0]);
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

    const handleSubmit = (e) => {
        e.preventDefault();
        const grievance = {
            grievance_id: id,
            grievance_status: grievanceStatus
        };
                  
        axios.post(defaultVariables['backend-url'] + "admin/grievance/update", grievance).then((res) => {
              setTimeout(() => {
                  setSuccessMessage("Updated successfully.");
              }, 3000)
            }).catch(err => {
              setErrorMessage("Some error occured");
              setTimeout(() => {
                  setErrorMessage(null);
              }, 3000)
        })
    };

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
            <p className='heading-medium'>Status</p>
            <div className='activity-buttons' style={{ display: "flex" }}>
                <Select fullWidth
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={grievanceStatus}
                    style={{ margin: '10px' }}
                    onChange={(e) => setGrievanceStatus(e.target.value)}
                >
                    <MenuItem value={0}>New</MenuItem>
                    <MenuItem value={1}>Assigned</MenuItem>
                    <MenuItem value={2}>Fixed</MenuItem>
                </Select>
                <Button style={{ margin: '10px' }} onClick={ handleSubmit } type="submit" variant="contained" color="primary" fullWidth id="react-button">
                    Submit
                </Button>
            </div>

            {successMessage && (
            <div style={{ position: 'fixed', top: '60px', right: '20px', zIndex: 9999 }}>
                <Alert severity="success" onClose={() => setSuccessMessage(null)} variant="standard">
                    <AlertTitle>Success</AlertTitle>
                    <strong>{successMessage}</strong>
                </Alert>
                </div>
            )}
            {errorMessage && (
                <div style={{ position: 'fixed', top: '60px', right: '20px', zIndex: 9999 }}>
                <Alert severity="error" onClose={() => setErrorMessage(null)} variant = "standard" width = {{sx : 300}}>
                    <AlertTitle>Error</AlertTitle>
                    <strong>{errorMessage}</strong>
                </Alert>
                </div>
            )}

        </div>
    )
};

export default AdminViewGrievance;