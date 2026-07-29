import { Routes, Route, Navigate } from "react-router-dom";
import { PrivateRoute } from "./PrivateRoute";
import { RoleBasedRoute } from "./RoleBasedRoute";

import { Login } from "../pages/auth/Login";
import { Register } from "../pages/auth/Register";
import { ForgotPassword } from "../pages/auth/ForgotPassword";
import { Landing } from "../pages/public/Landing";
import { Dashboard } from "../pages/dashboard/Dashboard";

import { StudentList } from "../pages/students/StudentList";
import { StudentDetail } from "../pages/students/StudentDetail";

import { ParentList } from "../pages/parents/ParentList";
import { ParentDetail } from "../pages/parents/ParentDetail";

import { FeeList } from "../pages/fees/FeeList";
import { FeeConfig } from "../pages/fees/FeeConfig";

import { PaymentList } from "../pages/payments/PaymentList";
import { NewPayment } from "../pages/payments/NewPayment";

import { InvoiceList } from "../pages/invoices/InvoiceList";
import { ReceiptView } from "../pages/invoices/ReceiptView";

import { OverdueList } from "../pages/overdue/OverdueList";
import { ReportsDashboard } from "../pages/reports/ReportsDashboard";
import { NotificationCenter } from "../pages/notifications/NotificationCenter";

import { UserList } from "../pages/users/UserList";
import { UserDetail } from "../pages/users/UserDetail";

import { UserProfile } from "../pages/profile/UserProfile";
import { InstitutionSettings } from "../pages/settings/InstitutionSettings";

export function AppRouter() {
  return (
    <Routes>
      <Route path="/" element={<Landing />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/forgot-password" element={<ForgotPassword />} />
      <Route
        path="/dashboard"
        element={
          <PrivateRoute>
            <Dashboard />
          </PrivateRoute>
        }
      />  
      <Route path="/students" element={<PrivateRoute><StudentList /></PrivateRoute>} />
      <Route path="/students/:id" element={<PrivateRoute><StudentDetail /></PrivateRoute>} />
      <Route path="/parents" element={<PrivateRoute><ParentList /></PrivateRoute>} />
      <Route path="/parents/:id" element={<PrivateRoute><ParentDetail /></PrivateRoute>} />
      <Route path="/fees" element={<PrivateRoute><FeeList /></PrivateRoute>} />
      <Route path="/fees/config" element={<PrivateRoute><FeeConfig /></PrivateRoute>} />
      <Route path="/payments" element={<PrivateRoute><PaymentList /></PrivateRoute>} />
      <Route path="/payments/new" element={<PrivateRoute><NewPayment /></PrivateRoute>} />
      <Route path="/invoices" element={<PrivateRoute><InvoiceList /></PrivateRoute>} />
      <Route path="/invoices/:id" element={<PrivateRoute><ReceiptView /></PrivateRoute>} />
      <Route path="/overdue" element={<PrivateRoute><OverdueList /></PrivateRoute>} />
      <Route path="/reports" element={<PrivateRoute><ReportsDashboard /></PrivateRoute>} />
      <Route path="/notifications" element={<PrivateRoute><NotificationCenter /></PrivateRoute>} />
      <Route path="/users" element={<PrivateRoute><UserList /></PrivateRoute>} />
      <Route path="/users/:id" element={<PrivateRoute><UserDetail /></PrivateRoute>} />
      <Route path="/profile" element={<PrivateRoute><UserProfile /></PrivateRoute>} />
      <Route path="/settings" element={<PrivateRoute><InstitutionSettings /></PrivateRoute>} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}