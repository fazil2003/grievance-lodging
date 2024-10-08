import React from 'react';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Box, Typography, Alert, AlertTitle } from '@mui/material';
import { useParams } from "react-router-dom";
import defaultVariables from '../variables/variables';
import './view-grievance.css';
import { useTranslation } from "react-i18next";

const SelectGrievance = () => {

    const { t } = useTranslation();

    let { id } = useParams();

    const navigate = useNavigate();

    const [grievance, setGrievance] = useState([]);
    const [departmentOne, setDepartmentOne] = useState([]);
    const [departmentTwo, setDepartmentTwo] = useState([]);
    const [departmentThree, setDepartmentThree] = useState([]);

    const [successMessage, setSuccessMessage] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    useEffect(() => {
        axios.get(defaultVariables['backend-url'] + "grievance/view/get?id=" + id)
            .then((res) => {
                setGrievance(res.data[0]);
                setDepartmentOne(res.data[0].grievanceDepartment[0]);
                if (res.data[0].grievanceDepartment[1]){
                    setDepartmentTwo(res.data[0].grievanceDepartment[1]);
                }
                else{
                    setDepartmentTwo({});
                }
                if (res.data[0].grievanceDepartment[2]){
                    setDepartmentThree(res.data[0].grievanceDepartment[2]);
                }
                else{
                    setDepartmentThree({});
                }
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    const [checkBoxOne, setCheckBoxOne] = useState(true);
    const [checkBoxTwo, setCheckBoxTwo] = useState(true);
    const [checkBoxThree, setCheckBoxThree] = useState(true);

    const [grievanceList, setGrievanceList] = useState([checkBoxOne, checkBoxTwo, checkBoxThree]);

    const toggleChangeBoxOne = () => {
        setGrievanceList([!checkBoxOne, checkBoxTwo, checkBoxThree]);
        setCheckBoxOne(!checkBoxOne);
    }
    const toggleChangeBoxTwo = () => {
        setGrievanceList([checkBoxOne, !checkBoxTwo, checkBoxThree]);
        setCheckBoxTwo(!checkBoxTwo);
    }
    const toggleChangeBoxThree = () => {
        setGrievanceList([checkBoxOne, checkBoxTwo, !checkBoxThree]);
        setCheckBoxThree(!checkBoxThree);
    }

    const navigateToHomeActivity = () => {
        navigate("/home");
    }

    const handleButtonClick = (e) => {
        e.preventDefault();
        const grievanceDetails = {
            grievance_id: id,
            grievance_option_1: checkBoxOne,
            grievance_option_2: checkBoxTwo,
            grievance_option_3: checkBoxThree
        };
                  
        axios.post(defaultVariables['backend-url'] + "grievance/update", grievanceDetails).then((res) => {
              setSuccessMessage(res.data);
              navigateToHomeActivity();
            }).catch(err => {
              setErrorMessage("Some error occured");
              setTimeout(() => {
                  setErrorMessage(null);
              }, 3000)
        })
    }

    return (
        <div className='activity-details'>
            <p className='heading-medium' >{ grievance.grievanceTitle }</p>
            <div className='all-details-div'>
                <div className='details-div'>
                    <p className='details-field'>{ t('title') }: </p>
                    <p className='details-value'>{ grievance.grievanceTitle }</p>
                </div>
                <div className='details-div'>
                    <p className='details-field'>{ t('description') }: </p>
                    <p className='details-value'>{ grievance.grievanceDescription }</p>
                </div>
                <div className='details-div'>
                    <p className='details-field'>{ t('departments') }: </p>
                    <p className='details-value'>
                        {
                        departmentOne && <div className='details-div'>
                            <input
                                type="checkbox"
                                checked={ checkBoxOne }
                                onChange={ toggleChangeBoxOne } />&nbsp;&nbsp;
                            <p className='details-value'>{ departmentOne.departmentName }</p>
                        </div>
                        }
                        {
                        departmentTwo && <div className='details-div'>
                            <input
                                type="checkbox"
                                checked={ checkBoxTwo }
                                onChange={ toggleChangeBoxTwo } />&nbsp;&nbsp;
                            <p className='details-value'>{ departmentTwo.departmentName }</p>
                        </div>
                        }
                        {
                        departmentThree && <div className='details-div'>
                            <input
                                type="checkbox"
                                checked={ checkBoxThree }
                                onChange={ toggleChangeBoxThree } />&nbsp;&nbsp;
                            <p className='details-value'>{ departmentThree.departmentName }</p>
                        </div>
                        }
                    </p>
                </div>

                <br /><br />
                <Button onClick={ handleButtonClick } type="submit" variant="contained" color="primary" fullWidth id="react-button">
                    { t('submit') }
                </Button>

            </div>
        </div>
    )
};

export default SelectGrievance;