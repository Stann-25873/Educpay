import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { invoiceService } from "../../services/invoiceService";
import { Card } from "../../components/common/Card";
import { Badge } from "../../components/common/Badge";
import { Button } from "../../components/common/Button";
import { HiOutlineArrowLeft, HiOutlinePrinter } from "react-icons/hi";

export function ReceiptView() {
  const { id } = useParams();
  const [invoice, setInvoice] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    invoiceService.getById(id)
      .then(setInvoice)
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <div className="p-6"><p className="text-gray-500">Chargement...</p></div>;
  if (!invoice) return (
    <div className="p-6">
      <p className="text-gray-500">Facture introuvable.</p>
      <Link to="/invoices" className="text-indigo-600 hover:underline mt-2 inline-block">Retour</Link>
    </div>
  );

  return (
    <div className="p-6 max-w-2xl mx-auto">
      <div className="flex items-center gap-4 mb-6">
        <Link to="/invoices" className="text-gray-400 hover:text-gray-600"><HiOutlineArrowLeft className="w-5 h-5" /></Link>
        <h1 className="text-2xl font-bold text-gray-900">Facture {invoice.invoiceNumber}</h1>
        <Badge variant={invoice.status === "PAID" ? "success" : "warning"} className="ml-2">{invoice.status}</Badge>
        <div className="ml-auto"><Button variant="outline" className="flex items-center gap-1"><HiOutlinePrinter className="w-4 h-4" /> Imprimer</Button></div>

      <Card className="p-8">
        <div className="text-center mb-8">
          <div className="w-12 h-12 bg-indigo-600 rounded-xl flex items-center justify-center mx-auto mb-2">
            <svg className="w-7 h-7 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
            </svg>
          </div>
          <h2 className="text-lg font-semibold">EduPay</h2>
          <p className="text-sm text-gray-500">{invoice.institutionName || "Établissement"}</p>
        </div>

        <div className="border-t pt-6 space-y-3">
          <div className="flex justify-between text-sm"><span className="text-gray-500">N° Facture</span><span className="font-medium">{invoice.invoiceNumber}</span></div>
          <div className="flex justify-between text-sm"><span className="text-gray-500">Étudiant</span><span className="font-medium">{invoice.studentName || "N/A"}</span></div>
          <div className="flex justify-between text-sm"><span className="text-gray-500">Frais</span><span className="font-medium">{invoice.feeTitle || "N/A"}</span></div>
          <div className="flex justify-between text-sm"><span className="text-gray-500">Date d'émission</span><span className="font-medium">{invoice.issueDate}</span></div>
          <div className="flex justify-between text-sm"><span className="text-gray-500">Échéance</span><span className="font-medium">{invoice.dueDate}</span></div>
        </div>
        <div className="border-t pt-4 mt-4">
          <div className="flex justify-between text-lg font-bold">
            <span>Total</span>
            <span>{invoice.totalAmount?.toLocaleString()} €</span>
          </div>
          {invoice.paidAmount > 0 && (
            <div className="flex justify-between text-sm text-green-600 mt-1">
              <span>Déjà payé</span>
              <span>{invoice.paidAmount?.toLocaleString()} €</span>
            </div>
          )}
        </div>
      </Card>
    </div>
    </div>
  );
}