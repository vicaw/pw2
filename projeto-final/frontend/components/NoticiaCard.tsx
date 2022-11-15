import React from 'react';
import { NoticiaCardType } from '../types/noticiacard';

const NoticiaCard = (noticia: NoticiaCardType) : JSX.Element => {
    return (
      <div className="grid grid-cols-12 gap-4 pt-8">
        <img className="col-span-4" src="https://s2.glbimg.com/1NEmfZRbdip7fYcEaMURjrymPBw=/0x0:1417x797/540x304/smart/filters:max_age(3600)/https://i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2022/s/S/Cn3tBASDyLDFJEec5rRQ/atraso.jpg" alt=""/>
        <div className="flex flex-col col-span-8 gap-2">
          <p className="text-gray-600 font-bold">{noticia.assunto}</p>  
          <p className="text-red-600 text-lg font-extrabold">{noticia.titulo}</p>   
          <p className="text-gray-500">{noticia.resumo}</p>
        </div>
      </div>
    );
  };
  
  export default NoticiaCard;