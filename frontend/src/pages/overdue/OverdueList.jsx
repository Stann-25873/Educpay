import { useState, useEffect } from "react";
import { invoiceService } from "../../services/invoiceService";
import { Card } from "../../components/common/Card";
import { Table } from "../../components/common/Table";
import { Badge } from "../../components/common/Badge";
import { Pagination } from "../../components/common/Pagination";

export function OverdueList() {
  const [invoices, setInvoices] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    const fetch = async () => {
      setLoading(true);
      try {
        const result = await invoiceService.getByStatus("OVERDUE");
        const data = Array.isArray(result) ? result : result.content || [];
        setInvoices(data);
        if (!Array.isArray(result)) setTotalPages(result.totalPages || 1);
      } catch (err) {
        console.error("Failed to load overdue invoices", err);
        setInvoices([]);
      } finally {
        setLoading(false);
      }
    };
    fetch();
  }, [page]);

  return (
    <div className="p-6 space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Impayés & Retards</h1>
        <p className="text-sm text-gray-500 mt-1">Factures en retard de paiement</p>
      </div>
      <Card className="p-5">
        <Table
          columns={[
            { key: "invoiceNumber", label: "N° Facture" },
            { key: "studentName", label: "Étudiant" },
            { key: "totalAmount", label: "Montant", render: (v) => `${v?.toLocaleString()} €` },
            { key: "dueDate", label: "Échéance" },
            { key: "status", label: "Statut", render: () => <Badge variant="danger">OVERDUE</Badge> },
          ]}
          data={invoices}
          loading={loading}
        />
        {totalPages > 1 && <div className="mt-4"><Pagination current={page} total={totalPages} onChange={setPage} /></div>}
      </Card>
    </div>
  );
}