import { useState, useEffect } from "react";
import { userService } from "../../services/userService";
import { Card } from "../../components/common/Card";
import { Table } from "../../components/common/Table";
import { Badge } from "../../components/common/Badge";
import { SearchBar } from "../../components/common/SearchBar";
import { Pagination } from "../../components/common/Pagination";

export function UserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    const fetch = async () => {
      setLoading(true);
      try {
        const result = await userService.list({ search, page, size: 10 });
        const data = Array.isArray(result) ? result : result.content || [];
        setUsers(data);
        if (!Array.isArray(result)) setTotalPages(result.totalPages || 1);
      } catch (err) {
        console.error("Failed to load users", err);
        setUsers([]);
      } finally {
        setLoading(false);
      }
    };
    fetch();
  }, [search, page]);

  return (
    <div className="p-6 space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Utilisateurs</h1>
        <p className="text-sm text-gray-500 mt-1">Gestion des comptes utilisateurs</p>
      </div>
      <Card className="p-5">
        <div className="mb-4"><SearchBar placeholder="Rechercher un utilisateur..." value={search} onChange={setSearch} /></div>
        <Table
          columns={[
            { key: "firstName", label: "Prénom" },
            { key: "lastName", label: "Nom" },
            { key: "email", label: "Email" },
            { key: "roleName", label: "Rôle", render: (v) => <Badge>{v}</Badge> },
            { key: "status", label: "Statut", render: (v) => <Badge variant={v === "ACTIVE" ? "success" : "danger"}>{v}</Badge> },
          ]}
          data={users}
          loading={loading}
          onRowClick={(u) => window.location.href = `/users/${u.id}`}
        />
        {totalPages > 1 && <div className="mt-4"><Pagination current={page} total={totalPages} onChange={setPage} /></div>}
      </Card>
    </div>
  );
}