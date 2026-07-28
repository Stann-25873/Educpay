import { Outlet } from "react-router-dom";

export function AuthLayout() {
  return (
    <div className="flex min-h-screen bg-edu-surface">
      <div className="flex flex-1 items-center justify-center px-4 py-12">
        <div className="w-full max-w-md">
          <div className="mb-8 text-center">
            <div className="mx-auto mb-4 flex h-12 w-12 items-center justify-center rounded-2xl bg-edu-primary">
              <span className="text-2xl font-bold text-white">E</span>
            </div>
            <h1 className="text-2xl font-bold text-edu-text-primary">EduPay</h1>
            <p className="mt-1 text-sm text-edu-muted">School Finance Platform</p>
          </div>
          <Outlet />
        </div>
    </div>
    </div>
  );
}
