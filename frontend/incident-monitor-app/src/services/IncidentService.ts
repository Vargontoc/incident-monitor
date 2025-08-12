import type { Incident } from "../models/Incident";
import type { SuggestResponse } from "../models/SuggestResponse";
import http from "./http";

export async function getIncidents(params: any) {
    const { data} = await http.get('/incidents', { params });
    return data as { content: Incident[]; totalElements: number; totalPages: number; size: number; number: number;}
}

export async function getIncident(id: string){
    const { data} = await http.get(`/incidents/${id}`)
    return data as Incident;
}

export async function createIncident(payload: {title: string, description?:string; priority: string}) {
    const { data } = await http.post('/incidents', payload);
    return data as Incident; 
}

export async function updateIncident(id: string, payload: Partial<Incident>) {
    const { data } = await http.put(`/incidents/${id}`, payload);
    return data as Incident; 
}

export async function suggest(incidentId: string, extras: string[] = []) {
    const { data } = await http.post(`/incidents/${incidentId}/suggest`, { contextExtras: extras});
    return data as SuggestResponse; 
}