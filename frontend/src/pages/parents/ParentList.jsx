import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { parentService } from "../../services/parentService";
import { Card } from "../../components/common/Card";
import { Table } from "../../components/common/Table";
import { Button } from "../../components/common/Button";
import { SearchBar } from "../../components/common/SearchBar";
import { Pagination } from "../../components/common/Pagination";
import { HiOutlinePlus } from "react-icons/hi";

export function ParentList() {
  const [parents, setParents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    const fetchParents = async () => {
      setLoading(true);
      try {
        const result = await parentService.list({ search, page, size: 10 });
        const data = Array.isArray(result) ? result : result.content || [];
        setParents(data);
        if (!Array.isArray(result)) setTotalPages(result.totalPages || 1);
      } catch (err) {
        console.error("Failed to load parents", err);
        setParents([]);
      } finally {
        setLoading(false);
      }
    };
    fetchParents();
  }, [search, page]);

  const columns = [
    { key: "firstName", label: "Prénom" },
    { key: "lastName", label: "Nom" },
    { key: "email", label: "Email" },
    { key: "phone", label: "Téléphone" },
  ];

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Parents</h1>
          <p className="text-sm text-gray-500 mt-1">Gérez les profils parents et tuteurs</p>
        </div>
        <Link to="/parents/new">
          <Button className="flex items-center gap-2">
            <HiOutlinePlus className="w-4 h-4" /> Nouveau parent
          </Button>
        </Link>
      </div>
      <Card className="p-5">
        <div className="mb-4">
          <SearchBar placeholder="Rechercher un parent..." value={search} onChange={setSearch} />
        </div>
        <Table columns={columns} data={parents} loading={loading}
          onRowClick={(p) => window.location.href = `/parents/${p.id}`} />
        {totalPages > 1 && (
          <div className="mt-4">
            <Pagination current={page} total={totalPages} onChange={setPage} />
          </div>
        )}
      </Card>
    </div>
  );
}