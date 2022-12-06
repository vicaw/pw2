import moment from "moment";
import {
  MODAL_TYPES,
  useGlobalModalContext,
} from "../../../contexts/ModalContext";
import { NoticiaType } from "../../../types/noticia";

interface Props {
  article: NoticiaType;
}

export default function ManageArticlesListItem({ article }: Props) {
  const { showModal } = useGlobalModalContext();

  const createModal = () => {
    showModal(MODAL_TYPES.CREATEARTICLE_MODAL, { article });
  };

  return (
    <tr
      onClick={createModal}
      className="hover:bg-gray-100 hover:text-red-700 cursor-pointer"
    >
      <td className="text-red-600 hover:text-red-700 font-bold text-lg text-left py-4 pl-2 tracking-tighter w-fit">
        {article.titulo}
      </td>

      <td className="mt-10 text-center tracking-tighter w-fit">
        {moment(article.updatedAt).format("DD/MM/YYYY HH[h]mm ")}
      </td>
      <td className="mt-10 text-center tracking-tighter w-fit">
        {moment(article.createdAt).format("DD/MM/YYYY HH[h]mm ")}
      </td>
    </tr>
  );
}
