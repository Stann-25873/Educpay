import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { studentService } from "../../services/studentService";
import { Card } from "../../components/common/Card";
import { Table } from "../../components/common/Table";
import { Badge } from "../../components/common/Badge";
import { Button } from "../../components/common/Button";
import { SearchBar } from "../../components/common/SearchBar";
import { Pagination } from "../../components/common/Pagination";
import { HiOutlinePlus } from "react-icons/hi";

export function StudentList() {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    const fetchStudents = async () => {
      setLoading(true);
      try {
        const result = await studentService.list({ search, page, size: 10 });
        const data = Array.isArray(result) ? result : result.content || [];
        setStudents(data);
        if (!Array.isArray(result)) {
          setTotalPages(result.totalPages || 1);
        }
      } catch (err) {
        console.error("Failed to load students", err);
        setStudents([]);
      } finally {
        setLoading(false);
      }
    };
    fetchStudents();
  }, [search, page]);

  const columns = [
    { key: "firstName", label: "Prénom" },
    { key: "lastName", label: "Nom" },
    { key: "level", label: "Niveau" },
    {
      key: "status",
      label: "Statut",
      render: (val) => (
        <Badge variant={val === "ACTIVE" ? "success" : "danger"}>{val}</Badge>
      ),
    },
  ];

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Étudiants</h1>
          <p className="text-sm text-gray-500 mt-1">Gérez les inscriptions et profils étudiants</p>
        </div>
        <Link to="/students/new">
          <Button className="flex items-center gap-2">
            <HiOutlinePlus className="w-4 h-4" />
            Nouvel étudiant
          </Button>
        </Link>
      </div>

      <Card className="p-5">
        <div className="mb-4">
          <SearchBar
            placeholder="Rechercher un étudiant..."
            value={search}
            onChange={setSearch}
          />
        </div>
        <Table
          columns={columns}
          data={students}
          loading={loading}
          onRowClick={(student) => window.location.href = `/students/${student.id}`}
        />
        {totalPages > 1 && (
          <div className="mt-4">
            <Pagination current={page} total={totalPages} onChange={setPage} />
          </div>
        )}
      </Card>
    </div>
  );
}