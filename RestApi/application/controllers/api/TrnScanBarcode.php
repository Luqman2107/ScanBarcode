<?php defined('BASEPATH') OR exit('No direct script access allowed');

use Restserver\Libraries\REST_Controller;

require APPPATH . '/libraries/REST_Controller.php';

class TrnScanBarcode extends REST_Controller {
    public function __construct() {
        parent::__construct();
        // Load Model
        $this->load->model('DataModel', 'data');
    }

    public function index_get()
    {
        $trn_scanbarcode = $this->db->get('trn_scanbarcode')->result_array();
        if ($trn_scanbarcode) {
            $this->response([
                'status' => TRUE,
                'data' => $trn_scanbarcode
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                'status' => FALSE,
                'message' => 'Data Transaksi Tidak Ditemukan'
            ], REST_Controller::HTTP_NOT_FOUND);
        }
    }

    public function index_post()
    {
        $arr = [
            'Adm_Name' => $this->input->post('Adm_Name'),
            'Line_No' => $this->input->post('Line_No'),
            'Station_Name' => $this->input->post('Station_Name'),
            'IO_Name' => $this->input->post('IO_Name'),
            'BarcodeNo' => $this->input->post('BarcodeNo'),
            'ScanDate' => date('Y-m-d H:i:s'),
        ];
        if ($this->data->insert('trn_scanbarcode', $arr)) {
            $response = [
                'status' => true,
                'pesan' => 'Data Scan berhasil ditambahkan.',
            ];
        $this->response($response, 200);
        } else {
            $response = [
                'status' => false,
                'pesan' => 'Data Scan gagal ditambahkan',
            ];
        $this->response($response, 400);
        }
    }
} 