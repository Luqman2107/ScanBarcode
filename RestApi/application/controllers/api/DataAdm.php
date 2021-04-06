<?php defined('BASEPATH') OR exit('No direct script access allowed');

use Restserver\Libraries\REST_Controller;

require APPPATH . '/libraries/REST_Controller.php';

class DataAdm extends REST_Controller {
    public function __construct() {
        parent::__construct();
        // Load Model
        // $this->load->model('DataModel', 'data');
    }

    public function index_get()
    {
        $data_adm = $this->db->get('data_adm')->result_array();
        if ($data_adm) {
            $this->response([
                'status' => TRUE,
                'data' => $data_adm
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                'status' => FALSE,
                'message' => 'Data Administrasi Tidak Ditemukan'
            ], REST_Controller::HTTP_NOT_FOUND);
        }
    }
} 