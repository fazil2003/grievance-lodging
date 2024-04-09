import SideBarOption from "./sidebar-option";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHandshake, faComment, faLocationArrow, faTable, faList, faUser, faPeopleCarry, faFire, faCalendarPlus, faMap, faDatabase, faImage } from '@fortawesome/fontawesome-free-solid'
import { useTranslation } from "react-i18next";

const SideBar = () => {

    const { t } = useTranslation();

    return (
        <div className="sidebar">
            <SideBarOption icon={<FontAwesomeIcon icon={faTable} />} title={ t('dashboard') } redirect={"dashboard"} />
            <SideBarOption icon={<FontAwesomeIcon icon={faList} />} title={ t('add_grievance') } redirect={"grievance/add"} />
            {/* <SideBarOption icon={<FontAwesomeIcon icon={faUser} />} title={"Beneficiaries"} redirect={"beneficiary/view"} />
            <SideBarOption icon={<FontAwesomeIcon icon={faPeopleCarry} />} title={"Communities"} redirect={"communities/view"} />
            <SideBarOption icon={<FontAwesomeIcon icon={faFire} />} title={"Sessions"} redirect={"sessions/view"} />
            <SideBarOption icon={<FontAwesomeIcon icon={faCalendarPlus} />} title={"Attendance"} redirect={"attendance/add"} />
            <SideBarOption icon={<FontAwesomeIcon icon={faComment} />} title={"Feedback"} redirect={"feedback/add"} />
            <SideBarOption icon={<FontAwesomeIcon icon={faLocationArrow} />} title={"Locations"} redirect={"locations/view"} />
            <SideBarOption icon={<FontAwesomeIcon icon={faHandshake} />} title={"Staffs"} redirect={"staffs/view"} />
            <p className="horizontal-divider"></p>
            <SideBarOption icon={<FontAwesomeIcon icon={faDatabase} />} title={"Data Upload"} redirect={"download-data"} />
            <SideBarOption icon={<FontAwesomeIcon icon={faImage} />} title={"Image to Text"} redirect={"image-to-text"} /> */}
        </div>
    )
}

export default SideBar;