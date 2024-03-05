import React from 'react';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from "react-router-dom";
import defaultVariables from '../variables/variables';

import './view-grievance.css';

const SelectGrievance = () => {

    // let { id } = useParams();
    const [grievance, setGrievance] = useState([]);
    const [departmentOne, setDepartmentOne] = useState([]);
    const [departmentTwo, setDepartmentTwo] = useState([]);
    const [departmentThree, setDepartmentThree] = useState([]);

    useEffect(() => {
        axios.get(defaultVariables['backend-url'] + "grievance/view/get?id=1")
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
            </div>
        </div>
    )
};

export default SelectGrievance;