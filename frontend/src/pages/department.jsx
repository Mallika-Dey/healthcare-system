import { useEffect, useState, useCallback } from "react";
import { Row, Pagination } from "antd";
import _ from "lodash";

import Department from "../components/cards/department.component";
import * as DeptService from "../service/adminService";
import SearchBarWithButton from "../components/searchBarWithButton";
import { MDBContainer, MDBRow } from "mdb-react-ui-kit";
import AddDeptModal from "../components/modals/addDeptModal.component";

function AllDepartments() {
  const [depts, setDepts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(9);
  const [length, setLength] = useState(0);
  const [searchResult, setResult] = useState([]);
  const userRole = localStorage.getItem("role");

  const fetchData = async () => {
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;

    DeptService.getAllDepartments()
      .then((response) => {
        setLength(response.length);
        setResult(response.slice(startIndex, endIndex));
        setDepts(response);
        setLoading(false);
      })
      .catch((error) => {
        setError(error);
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchData();
  }, [currentPage, itemsPerPage]);

  const openAddModal = () => {
    setIsAddModalOpen(true);
  };

  const handlePageChange = (page, pageSize) => {
    setCurrentPage(page);
    setItemsPerPage(pageSize);
  };

  const debounceSearch = useCallback(
    _.debounce((value) => {
      const filtered = depts.filter((dept) =>
        dept.deptName.toLowerCase().includes(value.toLowerCase())
      );
      setResult(filtered);
    }, 500),
    [depts]
  );

  const onSearchChange = (event) => {
    debounceSearch(event.target.value);
  };

  if (loading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p>Error: {error.message}</p>;
  }

  return (
    <div style={{ marginTop: "8em" }}>
      <h2 className="text-center mb-3">All Departments</h2>

      <SearchBarWithButton
        onSearchChange={onSearchChange}
        userRole={userRole}
        openAddModal={openAddModal}
      />
      <AddDeptModal
        isAddModalOpen={isAddModalOpen}
        setIsAddModalOpen={setIsAddModalOpen}
      />

      <div className="books-container flex justify-center">
        <MDBContainer className="mb-5 mt-5">
          <MDBRow style={{ marginTop: "20px" }}>
            {searchResult &&
              searchResult.map((department, index) => (
                <Department
                  key={index}
                  id={department.deptId}
                  title={department.deptName}
                  rooms={department.rooms}
                  className="my-4"
                  hoverable={true}
                />
              ))}
          </MDBRow>
        </MDBContainer>
      </div>

      <div className="d-flex justify-content-center">
        <Pagination
          current={currentPage}
          pageSize={itemsPerPage}
          total={length}
          onChange={handlePageChange}
        />
      </div>
    </div>
  );
}
export default AllDepartments;
