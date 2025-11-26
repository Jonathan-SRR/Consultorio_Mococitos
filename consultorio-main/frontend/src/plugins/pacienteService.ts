import api from '@/plugins/api';

export const normalizeRawPaciente = (data: any) => ({
  id: data.id_paciente ?? data.id ?? data.idPaciente ?? null,
  nombre: data.nombre_paciente ?? data.nombre ?? data.nombrePaciente ?? '',
  apellidoPaterno: data.apellido_paterno ?? data.apellidoPaterno ?? data.apellidoPaterno ?? '',
  apellidoMaterno: data.apellido_materno ?? data.apellidoMaterno ?? data.apellidoMaterno ?? '',
  fechaNacimiento: data.fecha_nacimiento ?? data.fechaNacimiento ?? data.fecha ?? null,
  sexo: data.sexo ?? data.genero ?? '',
  estatus: data.estatus ?? data.activo ?? true
});

export async function fetchPacienteWithTutor(id: number) {
  if (!id) throw new Error('ID de paciente requerido');

  const resp = await api.get(`/api/pacientes/${id}`);
  const data = resp.data;
  if (!data) return null;
  const paciente = normalizeRawPaciente(data);

  let tutor = null;
  try {
    const tutorResp = await api.get(`/api/pacientes/${paciente.id}/tutores`);
    if (tutorResp.data && tutorResp.data.length > 0) {
      tutor = tutorResp.data[0];
    }
  } catch (e) {}

  let direccion = null;
  try {
    if (tutor?.id_tutor) {
      const dirResp = await api.get(`/api/pacientes/tutores/${tutor.id_tutor}/direcciones`);
      if (dirResp.data && dirResp.data.length > 0) direccion = dirResp.data[0];
    }
  } catch (e) {}

  return { paciente, tutor, direccion };
}
