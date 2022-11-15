import type { NextPage } from 'next'
import Head from 'next/head'
import Image from 'next/image'
import NoticiaCard from '../components/NoticiaCard'
import { NoticiaCardType } from '../types/noticiacard'
import Header from "../components/Header";

const noticias2: NoticiaCardType[] = [
  { assunto: "ENEM", titulo: "Estudante chega cedo, sai para fumar e acaba perdendo o Enem", resumo: "Quando tentou voltar, encontrou os portões fechados." },
  { assunto: "ENEM", titulo: "Estudante chega cedo, sai para fumar e acaba perdendo o Enem", resumo: "Quando tentou voltar, encontrou os portões fechados." },
  { assunto: "ENEM", titulo: "Estudante chega cedo, sai para fumar e acaba perdendo o Enem", resumo: "Quando tentou voltar, encontrou os portões fechados." },
  { assunto: "ENEM", titulo: "Estudante chega cedo, sai para fumar e acaba perdendo o Enem", resumo: "Quando tentou voltar, encontrou os portões fechados." },
  { assunto: "ENEM", titulo: "Estudante chega cedo, sai para fumar e acaba perdendo o Enem", resumo: "Quando tentou voltar, encontrou os portões fechados." },
  { assunto: "ENEM", titulo: "Estudante chega cedo, sai para fumar e acaba perdendo o Enem", resumo: "Quando tentou voltar, encontrou os portões fechados." },
  { assunto: "ENEM", titulo: "Estudante chega cedo, sai para fumar e acaba perdendo o Enem", resumo: "Quando tentou voltar, encontrou os portões fechados." },
  { assunto: "ENEM", titulo: "Estudante chega cedo, sai para fumar e acaba perdendo o Enem", resumo: "Quando tentou voltar, encontrou os portões fechados." },
  { assunto: "ENEM", titulo: "Estudante chega cedo, sai para fumar e acaba perdendo o Enem", resumo: "Quando tentou voltar, encontrou os portões fechados." },
  { assunto: "ENEM", titulo: "Estudante chega cedo, sai para fumar e acaba perdendo o Enem", resumo: "Quando tentou voltar, encontrou os portões fechados." },
  { assunto: "ENEM", titulo: "Estudante chega cedo, sai para fumar e acaba perdendo o Enem", resumo: "Quando tentou voltar, encontrou os portões fechados." },
]

interface Props {
  noticias?: NoticiaCardType[];
}

const Home: NextPage<Props> = ({noticias}) => {
  return (
    <>
    <Header />

    <div className="container mx-auto px-10 mb-8">
      <Head>
        <title>Portal Noticias</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      
      <div className="grid grid-cols-4">
        <div className="flex flex-col divide-y col-span-3 gap-8">
          {noticias ? noticias.map((noticia) => (
            <NoticiaCard key={noticia.titulo} {...noticia} />
          )) : null}
        </div>

        <div className="">
          <p></p>
        </div>
      </div>

    </div>
    </>
  )
}


Home.getInitialProps = async () => {
  const data = await fetch('http://localhost:3000/api/noticias')
    .then((res) => res.json())
    .catch(() => noticias2);
  return { noticias: data }
}




export default Home