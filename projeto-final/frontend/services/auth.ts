import { AxiosError } from "axios";
import { api } from "./api";

export type SignInRequestData = {
  email: string;
  password: string;
}

export type RegistrationRequestData = {
  name: string;
  email: string;
  password: string;
}

export async function registrationRequest(data: RegistrationRequestData): Promise<any> {
  try{
    const res = await api.post(`http://localhost:8080/api/users`, data)
    const dados = await res.data
    return dados
  }
  catch(err){
    const errors = err as AxiosError<any>;
    throw errors?.response?.data.message;
  }
}


export async function signInRequest(data: SignInRequestData): Promise<any> {
  const res = await api.post(`http://localhost:8080/api/users/login`, data)
  const dados = await res.data
  return dados
}


export async function recoverUserInformation(userId: string) {
  const res = await api.get(`http://localhost:8080/api/users/${userId}`)
  const dados = await res.data
  return dados;
}