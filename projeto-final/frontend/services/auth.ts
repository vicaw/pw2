import { api } from "./api";

type SignInRequestData = {
  email: string;
  password: string;
}


export async function signInRequest(data: SignInRequestData): Promise<any> {
  const res = await api.post(`http://localhost:8080/api/users/login`, data)
  const dados = await res.data
  return dados
}



export async function recoverUserInformation(userId: string) {
  const res = await api.get(`http://localhost:8080/api/users/${userId}`)
  const dados = await res.data
  console.log(dados)
  return dados;
}