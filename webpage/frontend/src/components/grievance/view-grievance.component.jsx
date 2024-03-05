import React from 'react';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from "react-router-dom";
import defaultVariables from '../variables/variables';

import './view-grievance.css';

const ViewGrievance = () => {

    let { id } = useParams();
    const [grievance, setGrievance] = useState([]);

    useEffect(() => {
        axios.get(defaultVariables['backend-url'] + "grievance/view/get?id=" + id)
            .then((res) => {
                setGrievance(res.data[0]);
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
                {/* <div className='details-div'>
                    <p className='details-field'>Department: </p>
                    <p className='details-value'>{ grievance.grievanceDepartment }</p>
                </div> */}
            </div>
        </div>
    )
};

export default ViewGrievance;