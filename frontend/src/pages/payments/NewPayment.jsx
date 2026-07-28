import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { paymentService } from "../../services/paymentService";
import { studentService } from "../../services/studentService";
import { feeService } from "../../services/feeService";
import { Card } from "../../components/common/Card";
import { Button } from "../../components/common/Button";
import { PaymentForm } from "../../components/forms/PaymentForm";
import { HiOutlineArrowLeft } from "react-icons/hi";

export function NewPayment() {
  const navigate = useNavigate();
  const [students, setStudents] = useState([]);
  const [fees, setFees] = useState([]);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [studentsData, feesData] = await Promise.all([
          studentService.list({ size: 100 }),
          feeService.list({ size: 100 }),
        ]);
        setStudents(Array.isArray(studentsData) ? studentsData : studentsData.content || []);
        setFees(Array.isArray(feesData) ? feesData : feesData.content || []);
      } catch (err) {
        console.error("Failed to load form data", err);
      }
    };
    fetchData();
  }, []);

  const handleSubmit = async (formData) => {
    if (submitting) return;
    setSubmitting(true);
    try {
      await paymentService.create(formData);
      navigate("/payments");
    } catch (err) {
      console.error("Payment failed", err);
      alert("Erreur lors du paiement. Veuillez réessayer.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center gap-4">
        <button onClick={() => navigate("/payments")} className="text-gray-400 hover:text-gray-600">
          <HiOutlineArrowLeft className="w-5 h-5" />
        </button>
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Nouveau paiement</h1>
          <p className="text-sm text-gray-500 mt-1">Enregistrer un paiement manuel</p>
        </div>
      </div>
      <Card className="p-5 max-w-xl">
        <PaymentForm
          students={students}
          fees={fees}
          onSubmit={handleSubmit}
          onCancel={() => navigate("/payments")}
          submitting={submitting}
        />
      </Card>
    </div>
  );
}