"use client";
import React from "react";
import LoginForm from "../../components/client/login/loginform";
import { useModalContext } from "../../contexts/GlobalModalContext";
import {
  MODAL_TYPES,
  useGlobalModalContext,
} from "../../contexts/ModalContext";

export default function Login() {
  //const { openModal } = useModalContext();
  // const testModal = () => openModal({ message: "Olá, dev" });
  const { showModal } = useGlobalModalContext();
  const loginModal = () => {
    showModal(MODAL_TYPES.LOGIN_MODAL);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
      <button onClick={loginModal}>Abrir modal</button>
      <div className="max-w-sm w-full space-y-8">
        <div>
          <img
            className="mx-auto h-12 w-auto"
            src="https://tailwindui.com/img/logos/workflow-mark-indigo-600.svg"
            alt="Workflow"
          />
          <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
            Sign in to your account
          </h2>
        </div>
        <LoginForm />
      </div>
    </div>
  );
}
