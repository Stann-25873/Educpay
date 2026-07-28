import { useState, useEffect } from "react";
import { invoiceService } from "../../services/invoiceService";
import { Card } from "../../components/common/Card";
import { Table } from "../../components/common/Table";
import { Badge } from "../../components/common/Badge";
import { SearchBar } from "../../components/common/SearchBar";
import { Pagination } from "../../components/common/Pagination";

export function InvoiceList() {
  const [invoices, setInvoices] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    const fetch = async () => {
      setLoading(true);
      try {
        const result = await invoiceService.list({ search, page, size: 10 });
        const data = Array.isArray(result) ? result : result.content || [];
        setInvoices(data);
        if (!Array.isArray(result)) setTotalPages(result.totalPages || 1);
      } catch (err) {
        console.error("Failed to load invoices", err);
        setInvoices([]);
      } finally {
        setLoading(false);
      }
    };
    fetch();
  }, [search, page]);

  const statusVariant = (status) => {
    if (status === "PAID") return "success";
    if (status === "OVERDUE") return "danger";
    if (status === "PENDING") return "warning";
    return "default";
  };

  return (
    <div className="p-6 space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Factures</h1>
        <p className="text-sm text-gray-500 mt-1">Gérez les factures émises</p>
      </div>
      <Card className="p-5">
        <div className="mb-4"><SearchBar placeholder="Rechercher une facture..." value={search} onChange={setSearch} /></div>
        <Table
          columns={[
            { key: "invoiceNumber", label: "N° Facture" },
            { key: "studentName", label: "Étudiant" },
            { key: "totalAmount", label: "Montant", render: (v) => `${v?.toLocaleString()} €` },
            { key: "dueDate", label: "Échéance" },
            { key: "status", label: "Statut", render: (v) => <Badge variant={statusVariant(v)}>{v}</Badge> },
          ]}
          data={invoices}
          loading={loading}
          onRowClick={(inv) => window.location.href = `/invoices/${inv.id}`}
        />
        {totalPages > 1 && <div className="mt-4"><Pagination current={page} total={totalPages} onChange={setPage} /></div>}
      </Card>
    </div>
  );
}