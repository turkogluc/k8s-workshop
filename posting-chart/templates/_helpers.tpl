{{- define "mychart.labels" -}}
{{- $date := now | quote -}}
{{- $version := .Chart.AppVersion | quote -}}
  labels:
    current_date: {{ $date }}
    version: {{ $version }}
{{- end }}
